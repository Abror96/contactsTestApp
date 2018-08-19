 package com.example.abror.contactstestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference contactsRef = db.collection("Contacts");

    ArrayList<Contact> contactArrayList;

    private ContactAdapter adapter;
    private RecyclerView recyclerView;

    private View contextView;
    private SwipeRefreshLayout swipeToRefresh;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactArrayList = new ArrayList<>();
        contextView = findViewById(R.id.context_view);
        swipeToRefresh = findViewById(R.id.context_view);

        // checking internet connection
         if (isOffline()) {
             Snackbar.make(contextView, "There is no internet connection", Snackbar.LENGTH_LONG).show();
         }

        // opening add new contact activity
        FloatingActionButton buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewContactActivity.class));
            }
        });

        setUpRecyclerView();
        swipeToDelete();
        swipeToRefresh.setOnRefreshListener(
             new SwipeRefreshLayout.OnRefreshListener() {
                 @Override
                 public void onRefresh() {
                     if (isOffline()) {
                         Snackbar.make(contextView, "There is no internet connection", Snackbar.LENGTH_LONG).show();
                     }
                     if (contactArrayList.size() != 0) {
                         swipeToRefresh.setRefreshing(true);
                         loadDataFromFirebase();
                     } else {
                         swipeToRefresh.setRefreshing(false);
                         Snackbar.make(contextView, "Unable to update", Snackbar.LENGTH_SHORT).show();
                     }
                 }
             }
        );

    }

    public void loadDataFromFirebase() {
        if (contactArrayList.size() != 0) {
            int size = contactArrayList.size();
            contactArrayList.clear();
            adapter.notifyItemRangeRemoved(0, size);
        }

        contactsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               for (DocumentSnapshot documentSnapshot: task.getResult()) {
                   Contact contact = new Contact(
                           documentSnapshot.getString("fullname"),
                           documentSnapshot.getString("address"),
                           documentSnapshot.getString("email"),
                           documentSnapshot.getString("cellphone"),
                           documentSnapshot.getString("phone"),
                           documentSnapshot.getString("added_date"),
                           documentSnapshot.getId());
                   contactArrayList.add(contact);
               }

               contactArrayList.addAll(sortByName());

               adapter = new ContactAdapter(contactArrayList);
               recyclerView.getRecycledViewPool().clear();
               adapter.notifyDataSetChanged();
               recyclerView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(contextView, "Error while loading data", Snackbar.LENGTH_SHORT).show();
            }
        });
        swipeToRefresh.setRefreshing(false);
    }

    private ArrayList sortByName() {
        ArrayList<Contact> sortedList = new ArrayList();
        if (contactArrayList.size() > 0) {
            Collections.sort(contactArrayList, new Comparator<Contact>() {
                @Override
                public int compare(final Contact object1, final Contact object2) {
                   return object1.getFullname().compareTo(object2.getFullname());
                }
            });
        }
        return sortedList;
    }

    private void setUpRecyclerView() {
       recyclerView = findViewById(R.id.recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (contactArrayList.size() != 0) {
                    contactsRef.document(contactArrayList.get(viewHolder.getAdapterPosition()).getContactId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                    loadDataFromFirebase();
                                }
                             });
                    Snackbar.make(contextView, "Deleted successfully", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(contextView, "Try again", Snackbar.LENGTH_LONG).show();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu );
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
         return false;
     }

    @Override
    public boolean onQueryTextChange(String str) {
       str = str.toLowerCase();
       ArrayList<Contact> newlist = new ArrayList<>();
       for (Contact contact : contactArrayList) {
           String fullname = contact.getFullname().toLowerCase();
           String address = contact.getAddress().toLowerCase();
           if (fullname.contains(str) || address.contains(str)) {
               newlist.add(contact);
           }
       }
       adapter.setFilter(newlist);
       return true;
    }

    @Override
    protected void onStart() {
        loadDataFromFirebase();
        super.onStart();
    }

    public boolean isOffline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue != 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return true;
    }


}