package com.company;
import com.sun.nio.sctp.SctpSocketOption;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Terminal {

    public Parser parser=new Parser();

    public void chooseCommandAction() throws IOException{
        try {
            if("echo".equals(parser.getCommandName())){
                echo(parser.getArgs());
            }
            else if("pwd".equals(parser.getCommandName())){
                pwd();
            }
            else if("ls".equals(parser.getCommandName())){
                ls();
            }
            else if("ls -r".equals(parser.getCommandName())){
                ls_r();
            }
            else if("cat".equals(parser.getCommandName())){
                cat(parser.getArgs()[0]);
            }
            else if("rm".equals(parser.getCommandName())){
                rm(parser.getArgs()[0]);
            }
            else if("cp".equals(parser.getCommandName())){
                cp(parser.getArgs()[0], parser.getArgs()[1]);
            }
            else if("cp -r".equals(parser.getCommandName())){
                cp_r(parser.getArgs()[0], parser.getArgs()[1]);
            }
            else if("cd".equals(parser.getCommandName())){
                if(parser.getArgs().length==0)
                    cd(null);
                else
                    cd(parser.getArgs()[0]);
            }
            else if("mkdir".equals(parser.getCommandName())){
                mkdir(parser.getArgs());
            }
            else if("rmdir".equals(parser.getCommandName())){
                rmdir(parser.getArgs()[0]);
            }
            else if("touch".equals(parser.getCommandName())){
                touch(parser.getArgs()[0]);
            }
            else{
                System.err.println("Error: Ivalid Command");
            }
        } catch (Exception e) {
            System.err.println("Error: Ivalid Parameters");
        }

    }
    public void echo(String[] input){

        for (String i : input)
            System.out.print(i+' ');
        System.out.println();
    }
    public void pwd()
    {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
    }
    public void ls(){
        ArrayList<String>arraylist = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");

        File myObj = new File (currentDirectory); // created a file object named myObj
        File Directories [] = myObj.listFiles();
        for (int i =0; i<Directories.length; i++)
        {
            arraylist.add(Directories[i].getName());
        }
        Collections.sort(arraylist);
        System.out.println(arraylist);
    }
    public void ls_r(){
        ArrayList<String>arraylist = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");

        File myObj = new File (currentDirectory); // created a file object named myObj
        File Directories [] = myObj.listFiles();
        for (int i =0; i<Directories.length; i++)
        {
            arraylist.add(Directories[i].getName());
        }

        Collections.sort(arraylist);
        Collections.reverse(arraylist);
        System.out.println(arraylist);

    }
    public void mkdir(String[] arg){

        for (String dir : arg) {
            try {
                File check = new File(dir.substring(0, dir.lastIndexOf("\\")));

                if(check.exists()){
                    File f = new File(dir);
                    f.mkdir();
                }
                else{
                    System.err.println("Error: Invalid Path");
                }
            } catch (Exception e) {
                File f = new File(dir);
                f.mkdir();
            }
        }
    }
    public void rmdir(String arg){
        if("*".equals(arg)){
            String currentDirectory = System.getProperty("user.dir");
            File f = new File(currentDirectory);
            File[] dir = f.listFiles();
            for (File file : dir) {
                if(file.isDirectory()){
                    if(file.list().length==0){
                        file.delete();
                    }
                }
            }
        }
        else{
            File f = new File(arg);
            if(f.list().length==0){
                f.delete();
            }
            else{
                System.err.println(arg+" is not empty!");
            }
        }
    }

    public void touch(String fileName) throws IOException{
        try {
            File f = new File(fileName);
            f.createNewFile();
        } catch (Exception e) {
            System.err.println("Error: Invalid Path");
        }
    }

    public void cat(String fileName)
    {
        String currentDirectory = System.getProperty("user.dir");

        try {
            ArrayList <String> dataOnLine = new ArrayList<>();
            String info;
            File f=new File(currentDirectory+"\\"+fileName);
            Scanner MyReader = new Scanner ( f );
            while ( MyReader.hasNextLine ( ) ) {
                info = MyReader.nextLine( );
                dataOnLine.add ( info );
            }


            for (int i =0; i<dataOnLine.size(); i++)
            {
                System.out.println(dataOnLine.get(i));
            }
        } catch (Exception e) {
            System.out.println("File not exist !");
        }


    }
    public void rm(String fileName){
        String currentDirectory = System.getProperty("user.dir");
        try {
            File f=new File(currentDirectory+"\\"+fileName);
            if(f.isFile()){
                Scanner MyReader = new Scanner (f);
                MyReader.close();
            }

            f.delete();
        } catch (Exception e) {
            System.out.println("File not exist !");
        }


    }

    public void cp(String original,String copied){
        String currentDirectory = System.getProperty("user.dir");

        ArrayList <String> dataOnLine = new ArrayList<>();
        File myObj = new File ( currentDirectory+'\\'+original );
        try {
            Scanner MyReader = new Scanner( myObj );
            while ( MyReader.hasNextLine ( ) ) {
                String info = MyReader.nextLine ( );
                dataOnLine.add ( info );
            }
            MyReader.close();
            FileWriter myWriter = new FileWriter(currentDirectory+'\\'+copied);
            for (int i=0; i<dataOnLine.size(); i++)
            {
                myWriter.write(dataOnLine.get(i));
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (Exception e) {
            System.out.println("File not exist !");
        }
    }
    public void cp_r(String source, String dest){
        String currentDirectory = System.getProperty("user.dir");
        try {
            File srcDir = new File(currentDirectory+'\\'+source);
            File destDir = new File(currentDirectory+'\\'+dest);

            //FileUtils.copyDirectory(srcDir, destDir);


        } catch (Exception e) {
            System.out.println("source or destination directory not exist !");
        }

    }
    public void cd(String arg)
    {
        if(arg==null){
            System.setProperty("user.dir","C:\\Users\\hp\\Desktop");
            System.out.println(System.getProperty("user.dir"));
        }
        else{
            if ("..".equals(arg))
            {
                String current_path = System.getProperty("user.dir");
                if(current_path.endsWith(":")){
                    System.err.println("Error: You reach your root");
                }
                else
                {
                    int idx = current_path.lastIndexOf("\\");
                    String previouse_path =current_path.substring(0,idx);
                    System.setProperty("user.dir",previouse_path);
                    System.out.println(System.getProperty("user.dir"));
                }
            }
            else if(arg.contains(":"))
            {
                if(isPathValid(arg)){
                    System.setProperty("user.dir",arg);
                    System.out.println(System.getProperty("user.dir"));
                }
                else{
                    System.err.println("Error: Invalid Path");
                }

            }
            else
            {
                String current_path = System.getProperty("user.dir");
                if(isPathValid(arg)){
                    System.setProperty("user.dir",current_path+'\\'+arg);
                    System.out.println(System.getProperty("user.dir"));
                }
                else{
                    System.err.println("Error: Invalid Path");
                }

            }

        }
    }

    public static boolean isPathValid(String path) {

        try {
            System.out.println("terminal.Terminal.isPathValid()");
            String current_path = System.getProperty("user.dir");

            if(path.contains(":"))
            {
                File f = new File(path);
                return f.exists();
            }
            else{

                File f = new File(current_path+'\\'+path);
                return f.exists();
            }

        } catch (InvalidPathException ex) {
            System.out.println("Error:");
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws IOException {
        Terminal terminal = new Terminal();
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String command = input.nextLine();
            if("exit".equals(command))break;
            terminal.parser.parse(command);
            terminal.chooseCommandAction();
        }
    }
}