package thescone.uploaddownloadfiledemo.Download;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thescone.uploaddownloadfiledemo.R;

/**
 * Created by Habibah on 04-Mar-17.
 */
public class PDFAdapter extends ArrayAdapter<PDF> {

    Activity activity;
    int layoutResourceId;
    ArrayList<PDF> data=new ArrayList<PDF>();
    PDF pdf;

    public PDFAdapter(Activity activity, int layoutResourceId, ArrayList<PDF> data) {
        super(activity, layoutResourceId, data);
        this.activity=activity;
        this.layoutResourceId=layoutResourceId;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        PdfHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater= LayoutInflater.from(activity);
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new PdfHolder();
            holder.textViewName= (TextView) row.findViewById(R.id.textViewName);
            row.setTag(holder);
        }
        else
        {
            holder= (PdfHolder) row.getTag();
        }

        pdf = data.get(position);
        holder.textViewName.setText(pdf.getName());
        return row;
    }


    class PdfHolder
    {
        TextView textViewName;
    }
}
