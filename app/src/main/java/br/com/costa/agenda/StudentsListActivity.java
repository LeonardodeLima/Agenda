package br.com.costa.agenda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import br.com.costa.agenda.adapter.StudentAdapter;
import br.com.costa.agenda.dao.StudentDAO;
import br.com.costa.agenda.model.Student;

public class StudentsListActivity extends AppCompatActivity {

    ListView studentListView;

    @Override
    protected void onResume() {
        super.onResume();
        buildStudentsList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        studentListView = (ListView) findViewById(R.id.studentsList_listViewStudents);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Student student = (Student) studentListView.getItemAtPosition(position);
                Intent intentStudentInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                intentStudentInsert.putExtra("student", student);
                startActivity(intentStudentInsert);
            }
        });

        Button newButton = (Button) findViewById(R.id.studentsInsert_buttonNew);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStudentInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                startActivity(intentStudentInsert);
            }
        });

        registerForContextMenu(studentListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem callMenuItem = menu.add("Ligar");
        MenuItem showAddressMenuItem = menu.add("Localizar no mapa");
        MenuItem sendSmsMenuItem = menu.add("Enviar SMS");
        MenuItem goToSiteMenuItem = menu.add("Ir ao site");
        MenuItem deleteMenuItem = menu.add("Deletar");

        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);

        buildCall(callMenuItem, student);
        buildShowAddress(showAddressMenuItem, student);
        buildSendSms(sendSmsMenuItem, student);
        buildGoToSiteMenu(goToSiteMenuItem, student);
        buildDeleteMenu(deleteMenuItem, student);
    }

    private void buildCall(final MenuItem callMenuItem, final Student student) {

        callMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (ActivityCompat.checkSelfPermission(StudentsListActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(StudentsListActivity.this,
                                new String[]{android.Manifest.permission.CALL_PHONE}, 123);
                }else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + student.getNumber()));
                    startActivity(callIntent);
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 123){
            // executa algo após permissão
        }
    }

    private void buildShowAddress(MenuItem showAddressMenuItem, Student student) {
        Intent showAddressIntent = new Intent(Intent.ACTION_VIEW);
        showAddressIntent.setData(Uri.parse("geo:0,0?q=" + student.getAddress()));
        showAddressMenuItem.setIntent(showAddressIntent);
    }

    private void buildSendSms(MenuItem sendSmsMenuItem, Student student) {
        Intent sendSmsIntent = new Intent(Intent.ACTION_VIEW);
        sendSmsIntent.setData(Uri.parse("sms:" + student.getNumber()));
        sendSmsMenuItem.setIntent(sendSmsIntent);
    }

    private void buildGoToSiteMenu(MenuItem goToSiteMenu, Student student) {
        Intent goToSiteIntent = new Intent(Intent.ACTION_VIEW);

        String site = student.getSite();

        if(!site.startsWith("http://")){
            goToSiteIntent.setData(Uri.parse("http://" + site));
        }else{
            goToSiteIntent.setData(Uri.parse(site));
        }

        goToSiteMenu.setIntent(goToSiteIntent);
    }

    private void buildDeleteMenu(MenuItem deleteMenuItem, final Student student) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
                studentDAO.delete(student.getId());
                studentDAO.close();

                buildStudentsList();
                Toast.makeText(StudentsListActivity.this, "Registro de/da: " + student.getName() + " removido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    private void buildStudentsList() {

        StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
        List<Student> studentList = studentDAO.read();
        studentDAO.close();

        StudentAdapter studentsListViewAdapter = new StudentAdapter(this, studentList);
        studentListView.setAdapter(studentsListViewAdapter);
    }

}
