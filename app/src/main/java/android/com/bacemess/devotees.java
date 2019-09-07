package android.com.bacemess;

import android.app.ActionBar;
import android.app.Dialog;
import android.com.bacemess.Modal.names_wrapper;
import android.com.bacemess.names.NamesAdapter;
import android.com.bacemess.names.names_modal;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class devotees extends AppCompatActivity {
    public List<names_modal> li = new ArrayList();
    public List<Integer> extras_list;
    private RecyclerView recyclerView;
    private NamesAdapter mAdapter;
    private TextView breakfast,lunch,dinner;
    TextInputEditText d_breakfast,d_lunch,d_dinner,d_person;
    private ProgressBar pb,d_pb;
    android.support.design.widget.FloatingActionButton extra;
    Button d_add_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotees);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        breakfast = findViewById(R.id.breakfast);
        lunch = findViewById(R.id.lunch);
        dinner = findViewById(R.id.dinner);
        pb = findViewById(R.id.progressbar);
        extra = findViewById(R.id.extra);


        mAdapter = new NamesAdapter(li);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        setCount();

        prepareData();

    }

    private void setCount()  {
        Upload  ui= ApiUtils.retroEntity().create(Upload.class);
        Call<List<Integer>> call=ui.get_count();
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> li =response.body();
                breakfast.setText(""+li.get(0));
                lunch.setText(""+li.get(1));
                dinner.setText(""+li.get(2));
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
              Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareData() {
/*       names_modal obj = new names_modal();
       obj.setName("Nilesh Dahiphale");
       obj.setBreakfast("yes");
       obj.setLunch("yes");
       obj.setDinner("no");
        li.add(obj);
          Log.e("tag ","tagzefd");
        obj = new names_modal();
        obj.setName("Nitesh Chaudhari");
        obj.setBreakfast("yes");
        obj.setLunch("yes");
        obj.setDinner("no");
        Log.e("tag ","ta");
        li.add(obj);*/

        pb.setVisibility(View.VISIBLE);

        Upload  ui= ApiUtils.retroEntity().create(Upload.class);
        Call<List<names_modal>> call=ui.get_data();

        call.enqueue(new Callback<List<names_modal>>() {
            @Override
            public void onResponse(Call<List<names_modal>> call, Response<List<names_modal>> response) {
               pb.setVisibility(View.INVISIBLE);
                List<names_modal> li = response.body();
                mAdapter = new NamesAdapter(li);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<names_modal>> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
               Toast.makeText(getApplicationContext(),Reusable.failure,Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void commit_changes(View v) {
      Map<Integer,names_modal> map = NamesAdapter.getModifiedMap();
      printMap(map);
      pb.setVisibility(View.VISIBLE);

      if(map.size()>0) {

          List<names_modal> li = new ArrayList();
          for(Map.Entry en:map.entrySet()) {
              li.add((names_modal) en.getValue());
          }

          names_wrapper nw = new names_wrapper();
          nw.setLi(li);

          Upload ui = ApiUtils.retroEntity().create(Upload.class);
          Call<UploadObject> call = ui.commit_data(nw);
          call.enqueue(new Callback<UploadObject>() {
              @Override
              public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                  pb.setVisibility(View.INVISIBLE);
                  if(response.body().getStatus().equalsIgnoreCase("success")) {

                      Toast.makeText(getApplicationContext(), "changes saved successfully.", Toast.LENGTH_SHORT).show();
                      setCount();
                      Log.e("tag", "map status : " + response.body().getStatus());
                  }else {

                      Toast.makeText(getApplicationContext(), "Something went wrong.try again later.", Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onFailure(Call<UploadObject> call, Throwable t) {
                  pb.setVisibility(View.INVISIBLE);
                  Log.e("tag","error commit_data : "+t.getMessage());
                  Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
              }
          });
      } else {
          pb.setVisibility(View.INVISIBLE);
          Toast.makeText(getApplicationContext(),"No changes made",Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }



    public void printMap(Map<Integer,names_modal> map) {
       for( Map.Entry<Integer,names_modal>  en :map.entrySet())
       {
           names_modal x = en.getValue();
           Log.e("tag","key:"+en.getKey()+"\n"+"id:"+x.getId()+" name:"+x.getName()+" breakfast:"+x.getBreakfast()
           +" lunch:"+x.getLunch()+" dinner:"+x.getDinner());
       }
    }

    public void addExtras(View view) {
        final Dialog d=new Dialog(view.getContext());
        d.getWindow().setContentView(R.layout.extra_count);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();


      final  Button save_count;

        d_breakfast = d.findViewById(R.id.breakfast);
        d_lunch = d.findViewById(R.id.lunch);
        d_dinner = d.findViewById(R.id.dinner);
        save_count = d.findViewById(R.id.save_count);
        d_pb = d.findViewById(R.id.progressbar);


        get_extras();

        save_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag","breakfast "+d_breakfast.getText()+" lunch "+d_lunch.getText()+" dinner "+d_dinner.getText());
                Upload  ui= ApiUtils.retroEntity().create(Upload.class);
                Call<UploadObject> call=ui.save_extra_count(d_breakfast.getText().toString().trim(),
                        d_lunch.getText().toString().trim(),
                        d_dinner.getText().toString().trim()
                        );
                call.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        d.dismiss();
                        if(response.body().getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(),"counts added",Toast.LENGTH_SHORT).show();
                            setCount();
                        }else{
                            Toast.makeText(getApplicationContext(),"Something went wrong.Try again later",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                     d.dismiss();
                     Log.e("tag","extra count error : "+t.getMessage());
                     Toast.makeText(getApplicationContext(),Reusable.failure,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public void get_extras() {

        d_pb.setVisibility(View.VISIBLE);


        Upload  ui= ApiUtils.retroEntity().create(Upload.class);
        Call<List<Integer>> call=ui.get_extras();
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
             d_pb.setVisibility(View.INVISIBLE);
                extras_list = response.body();
                Log.e("tag","extras_list "+extras_list.size());
                Log.e("tag",""+extras_list.get(0));
                d_breakfast.setText(""+extras_list.get(0));
                d_lunch.setText(""+extras_list.get(1));
                d_dinner.setText(""+extras_list.get(2));

            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
             d_pb.setVisibility(View.INVISIBLE);
             Toast.makeText(getApplicationContext(),Reusable.failure,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_button, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.add_person:
                // search action
                if(LoginActivity.user.equalsIgnoreCase("krsna")&&LoginActivity.pass.equalsIgnoreCase("balaram")) {
                    final Dialog d=new Dialog(getApplicationContext());
                    d.getWindow().setContentView(R.layout.add_name);
                    d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    d.show();

                    d_person  = findViewById(R.id.person);
                    d_add_person = findViewById(R.id.add_person);

                    d_add_person.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = d_person.getText().toString().trim();
                            if(!s.isEmpty()) {
                                Upload  ui= ApiUtils.retroEntity().create(Upload.class);
                                Call<UploadObject> call= ui.add_person(s);
                                call.enqueue(new Callback<UploadObject>() {
                                    @Override
                                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                                        d.dismiss();
                                        if(response.body().getStatus().equalsIgnoreCase("success")) {
                                            Toast.makeText(getApplicationContext(),"Name added",Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),Reusable.failure,Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UploadObject> call, Throwable t) {
                                     d.dismiss();
                                        Toast.makeText(getApplicationContext(),Reusable.failure,Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }else {
                                Toast.makeText(getApplicationContext(),"Empty field is not allowed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(),"You don't have priviledge to add person",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
