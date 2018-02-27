package com.quintana.daniela.calculator;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Evalua el caracter a imprimir en pantalla y lo imprime
    public void printChar(View v) {
        Button b = (Button) v;
        String a = b.getText().toString();

        TextInputEditText txt = (TextInputEditText) findViewById(R.id.txtNum);
        TextInputEditText res = (TextInputEditText) findViewById(R.id.txtRes);

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
            if(txt.getText().toString().equals("")) {
                return;
            }
            txt.setText(txt.getText().toString()+"+");

        }else if(a.equals("-")) {

            if(txt.getText().toString().equals("")) {
                return;
            }
            txt.setText(txt.getText().toString()+"-");

        }else if(a.equals("X")) {

            if(txt.getText().toString().equals("")) {
                return;
            }
            txt.setText(txt.getText().toString()+"*");

        }else if(a.equals("/")) {

            if(txt.getText().toString().equals("")) {
                return;
            }
            txt.setText(txt.getText().toString()+"/");

        }else if(a.equals("(")) {

            txt.setText(txt.getText().toString()+"(");

        }else if(a.equals(")")) {

            txt.setText(txt.getText().toString()+")");

        }else if(a.equals("^")) {

            txt.setText(txt.getText().toString()+"^");

        }else if(a.equals("sin")) {

            txt.setText(txt.getText().toString()+"sin(");

        }else if(a.equals("cos")) {

            txt.setText(txt.getText().toString()+"cos(");

        }else if(a.equals("tan")) {

            txt.setText(txt.getText().toString()+"tan(");

        } else if (a.equals("=")) {

            String mathExpr = infijoAPostfijo(txt.getText().toString());
            Log.d("RES", ""+funcionEvaluaPostFijo(mathExpr));
            res.setText(""+funcionEvaluaPostFijo(mathExpr));

        }else {
            if(txt.getText().toString().equals("0")) {
                txt.setText(a);
            }else {
                txt.setText(txt.getText() + a);
            }
        }

    }

    /**
     * @param op
     * @return Jerarquia de operaciones
     */
    private static int pref(String op) {
        int prf = 99;
        if (op.equals("^")) prf = 5;
        if (op.equals("*") || op.equals("/")) prf = 4;
        if (op.equals("+") || op.equals("-")) prf = 3;
        if (op.equals(")")) prf = 2;
        if (op.equals("(")) prf = 1;
        return prf;
    }

    /**
     * @param s
     * @return Elimina espacios en blanco e inserta los operadores
     */
    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String simbols = "+-*/()^";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            }else str += s.charAt(i);
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    /**
     * @param expresion
     * @return Convierte la expresion infija a postfija
     */
    public String infijoAPostfijo(String expresion){
        //Depurar la expresion algebraica
        String expr = depurar(expresion);
        String[] arrayInfix = expr.split(" ");
        String literales = "x";

        //Declaración de las pilas
        Stack < String > E = new Stack < String > (); //Pila entrada
        Stack < String > P = new Stack < String > (); //Pila temporal para operadores
        Stack < String > S = new Stack < String > (); //Pila salida

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfix.length - 1; i >= 0; i--) {
            E.push(arrayInfix[i]);
        }

        try {
            //Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())){
                    case 1:
                        P.push(E.pop());
                        break;
                    case 3:
                    case 4:
                        while(pref(P.peek()) >= pref(E.peek())) {
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    case 2:
                        while(!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    case 5:
                        while(pref(P.peek()) >pref(E.peek())){
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    default:
                        if(literales.contains(E.peek().toLowerCase())){
                            S.push(E.pop().toLowerCase());
                        }
                        else{
                            S.push(E.pop());
                        }
                }
            }

            //Eliminacion de `impurezas´ en la expresiones algebraicas
            String infix = expr.replace(" ", "");
            String postfix = S.toString().replaceAll("[\\]\\[,]", "");

            //Mostrar resultados:
            System.out.println("Expresion Infija: " + infix);
            System.out.println("Expresion Postfija: " + postfix);
            return postfix;

        }catch(Exception ex){
            System.out.println("Error en la expresión algebraica");
            System.err.println(ex);
        }
        return null;
    }

    /**
     * @param expPostFijo
     * @return Evalua la expreion postfijo
     */
    private Double funcionEvaluaPostFijo(String expPostFijo)
    {
        String[] post = expPostFijo.split(" ");

        // Declaración de las pilas
        Stack<String> E = new Stack<String>(); // Pila entrada
        Stack<String> P = new Stack<String>(); // Pila de operandos

        // Añadir post (array) a la Pila de entrada (E)
        for (int i = post.length - 1; i >= 0; i--) {
            E.push(post[i]);
        }

        String operadores = "+-*/^";
        while (!E.isEmpty()) {
            if (operadores.contains("" + E.peek()))
            {
                Double resParcial = evaluar(E.pop(), P.pop(), P.pop());
                if(resParcial != null)
                    P.push( resParcial + "");
                else
                    return null;
            }
            else
            {
                P.push(E.pop());
            }
        }
        Double resultado = Double.parseDouble(P.pop());
        if(P.isEmpty()){
            return resultado;
        }
        return null;
    }

    /**
     * @param op
     * @param n2
     * @param n1
     * @return Evalua los operadores.
     */
    private static Double evaluar(String op, String n2, String n1) {
        try
        {
            double num1 = Double.parseDouble(n1);
            double num2 = Double.parseDouble(n2);
            if (op.equals("+"))
                return (num1 + num2);
            if (op.equals("-"))
                return (num1 - num2);
            if (op.equals("*"))
                return (num1 * num2);
            if (op.equals("/"))
                return (num1 / num2);
            if (op.equals("^"))
                return (Math.pow(num1, num2));
            if (op.equals("sin"))
                return Math.sin(num1);
            return 0d;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
