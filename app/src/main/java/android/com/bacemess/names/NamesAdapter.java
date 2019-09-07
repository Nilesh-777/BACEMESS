package android.com.bacemess.names;

import android.com.bacemess.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.*;
public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.MyViewHolder> {
    names_modal nm;
    int r;
    private List<names_modal> names_list;
    public static Map<Integer,names_modal> map = new HashMap();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox breakfast,lunch,dinner;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            breakfast = view.findViewById(R.id.breakfast);
            lunch = view.findViewById(R.id.lunch);
            dinner = view.findViewById(R.id.dinner);

            breakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stat = "";
                    r = getAdapterPosition();
                    nm = names_list.get(r);

                    if(map.get(r) == null)
                        map.put(r,nm);
                    else {
                        nm = map.get(r);
                    }


                    if(breakfast.isChecked()) {
                        stat = "yes";
                        Log.e("tag","stat : "+stat);
                    }
                    else {
                        stat = "no";
                        Log.e("tag","stat : "+stat);
                    }
                    // printNM(nm);
                    // printNM(nm);
                    nm.setBreakfast(stat);
                    map.put(r,nm);
                    //  printMap(map);
                }

            });

            lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stat = "";
                    r = getAdapterPosition();
                    nm = names_list.get(r);

                    if(map.get(r) == null)
                        map.put(r,nm);
                    else {
                        nm = map.get(r);
                    }

                    if(lunch.isChecked()) {
                        stat = "yes";
                        Log.e("tag","stat : "+stat);
                    }
                    else {
                        stat = "no";
                        Log.e("tag","stat : "+stat);
                    }
                    nm.setLunch(stat);
                    map.put(r,nm);
                    // printMap(map);
                }
            });

            dinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stat = "";
                    r = getAdapterPosition();
                    nm = names_list.get(r);

                    if(map.get(r) == null)
                        map.put(r,nm);
                    else {
                        nm = map.get(r);
                    }


                    if(dinner.isChecked()) {
                        stat = "yes";
                        Log.e("tag","stat : "+stat);
                    }
                    else {
                        stat = "no";
                        Log.e("tag","stat : "+stat);
                    }

                    nm.setDinner(stat);
                    map.put(r,nm);
                    Log.e("tag","dinner map size : "+map.size()+" r:"+r+"name:"+map.get(r).getId());
                    // printMap(map);
                }
            });
        }
    }


    public NamesAdapter(List<names_modal> names_list) {
        this.names_list = names_list;
        Log.e("tag","size:"+names_list.size()+"");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.names_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        names_modal name = names_list.get(position);
        holder.name.setText(name.getName());

        if(name.getBreakfast().equalsIgnoreCase("yes"))
            holder.breakfast.setChecked(true);
        else
            holder.breakfast.setChecked(false);

        if(name.getLunch().equalsIgnoreCase("yes"))
            holder.lunch.setChecked(true);
        else
            holder.lunch.setChecked(false);

        if(name.getDinner().equalsIgnoreCase("yes"))
            holder.dinner.setChecked(true);
        else
            holder.dinner.setChecked(false);



    }

    @Override
    public int getItemCount() {
        return names_list.size();
    }

    public static Map<Integer,names_modal> getModifiedMap() {
        return map;
    }

    public void printMap(Map<Integer,names_modal> map) {
        for( Map.Entry<Integer,names_modal>  en :map.entrySet())
        {
            names_modal x = en.getValue();
            Log.e("tag","key:"+en.getKey()+"\n"+"id:"+x.getId()+" name:"+x.getName()+" ["+x.getBreakfast()
                    +" "+x.getLunch()+" "+x.getDinner()+"]");
        }
    }

    public void printNM(names_modal x) {
        Log.e("tag","{NM\n"+"id:"+x.getId()+" name:"+x.getName()+" "+x.getBreakfast()
                +" "+x.getLunch()+" "+x.getDinner()+"}");
    }
}