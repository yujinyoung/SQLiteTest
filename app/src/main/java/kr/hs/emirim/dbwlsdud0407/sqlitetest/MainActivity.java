package kr.hs.emirim.dbwlsdud0407.sqlitetest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editName,editCount, editNameResult,editCountResult;
    MyDBHelper dbhelper;
    SQLiteDatabase db;
    Button butinit,butinput,butselect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhelper=new MyDBHelper(this); //idol db 생성
        setContentView(R.layout.activity_main);
        editName=(EditText)findViewById(R.id.edit_groupname);
        editCount=(EditText)findViewById(R.id.edit_groupcount);
        editNameResult=(EditText)findViewById(R.id.edit_nameResult);
        editCountResult=(EditText)findViewById(R.id.edit_countResult);
        butinit=(Button)findViewById(R.id.but_init);
        butinput=(Button)findViewById(R.id.but_input);
        butselect=(Button)findViewById(R.id.but_select);

        butinit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                db=dbhelper.getWritableDatabase();
                dbhelper.onUpgrade(db,1,2);
                db.close();
            }
        });

        butinput.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                db=dbhelper.getWritableDatabase();
                db.execSQL("insert into idoltable values('"+editName.getText().toString()+"', "
                        +editCount.getText().toString()+
                        ")");
                db.close();
                Toast.makeText(getApplicationContext(),"정상적으로 입력완료",Toast.LENGTH_SHORT).show();
            }

        });

        butselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=dbhelper.getReadableDatabase();
                Cursor rs=db.rawQuery("select * from idoltable",null);
                String gname="그룹이름"+"\n"+"----------"+"\n";
                String gcount="인원수"+"\n"+"----------"+"\n";
                while(rs.moveToNext()){
                    gname+=rs.getString(0)+"\n";
                    gcount+=rs.getString(1)+"\n";
                }
                editNameResult.setText(gname);
                editCountResult.setText(gcount);

                rs.close();
                db.close();
            }
        });
    }

    public class MyDBHelper extends SQLiteOpenHelper{
        MyDBHelper(Context context){
            super(context, "idoldb",null,1);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL("create table idoltable(gname char(40),primary key. gcount integer);");
        }
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
            db.execSQL("drop table if exist idoltable ");
            onCreate(db);
        }


    }
}
