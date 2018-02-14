package com.quintana.daniela.calculator;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String tmp = "";
    String op = "";
    public void printChar(View v) {
        Button b = (Button) v;
        String a = b.getText().toString();

        TextInputEditText txt = (TextInputEditText) findViewById(R.id.txtNum);

        if(a.equals("")) {
            txt.setText("0");
        }

        if(a.equals("C")) {
            txt.setText("");
        }else if(a.equals("DEL")) {
            if(txt.getText().toString().length()<1) {
                return;
            }
            Log.d("BTN", txt.getText().toString().substring(0,txt.getText().toString().length()-1));
            txt.setText(txt.getText().toString().substring(0,txt.getText().toString().length()-1));
        } else if(a.equals("+")) {
            if(tmp.equals("")) {
                tmp = txt.getText().toString();
                txt.setText("0");
                op = "+";
            } else {
                int res = Integer.parseInt(tmp) + Integer.parseInt(txt.getText().toString());
                tmp = "";
                op = "";
                txt.setText("" + res);
            }
        }else if(a.equals("-")) {
            if(tmp.equals("")) {
                tmp = txt.getText().toString();
                txt.setText("0");
                op = "-";
            } else {
                int res = Integer.parseInt(tmp) - Integer.parseInt(txt.getText().toString());
                tmp = "";
                op = "";
                txt.setText(""+res);
            }
        }else if(a.equals("X")) {
            if(tmp.equals("")) {
                tmp = txt.getText().toString();
                txt.setText("0");
                op = "*";
            } else {
                int res = Integer.parseInt(tmp) * Integer.parseInt(txt.getText().toString());
                tmp = "";
                op = "";
                txt.setText(""+res);
            }
        }else if(a.equals("/")) {
            if(tmp.equals("")) {
                tmp = txt.getText().toString();
                txt.setText("0");
                op = "/";
            } else {
                int res = Integer.parseInt(tmp) / Integer.parseInt(txt.getText().toString());
                tmp = "";
                op = "";
                txt.setText(""+res);
            }
        } else if (a.equals("=")) {
            if (op.equals("")) {
                return;
            }else if (op.equals("+")) {
                int res = Integer.parseInt(tmp) + Integer.parseInt(txt.getText().toString());
                txt.setText(""+res);
                tmp="";
                op="";
            }else if (op.equals("-")) {
                int res = Integer.parseInt(tmp) - Integer.parseInt(txt.getText().toString());
                txt.setText(""+res);
                tmp="";
                op="";
            }else if (op.equals("X")) {
                int res = Integer.parseInt(tmp) * Integer.parseInt(txt.getText().toString());
                txt.setText(""+res);
                tmp="";
                op="";
            }else if (op.equals("/")) {
                int res = Integer.parseInt(tmp) / Integer.parseInt(txt.getText().toString());
                txt.setText(""+res);
                tmp="";
                op="";
            }
        }else {
            if(txt.getText().toString().equals("0")) {
                txt.setText(a);
            }else {
                txt.setText(txt.getText() + a);
            }
        }


        Log.d("BTN", tmp);

    }
}
