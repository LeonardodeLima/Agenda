package br.com.costa.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.costa.agenda.R;
import br.com.costa.agenda.StudentsListActivity;
import br.com.costa.agenda.model.Student;


public class StudentAdapter extends BaseAdapter {

    private final Context context;
    private final List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return this.students.size();
    }

    @Override
    public Object getItem(int position) {
        return this.students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Student student = this.students.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.student_list, parent, false);
        }
        TextView studentName = (TextView) view.findViewById(R.id.studentList_textViewName);
        studentName.setText(student.getName());

        TextView studentPhone = (TextView) view.findViewById(R.id.studentList_textViewPhone);
        studentPhone.setText(student.getNumber());

        ImageView studentPhoto = (ImageView) view.findViewById(R.id.studentList_imageViewPhoto);
        if (student.getPathPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(student.getPathPhoto());
            Bitmap bitmapReduce = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            studentPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            studentPhoto.setImageBitmap(bitmapReduce);
        }

        return view;
    }
}
