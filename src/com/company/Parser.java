package com.company;

public class Parser {
    String commandName ;
    String[] args ={};
    public boolean parse(String input)
    {
        args = new String[0];
        try {
            String[] splited = input.split(" ");
            if(splited.length>1){
                if("-r".equals(splited[1])){
                    commandName=splited[0]+' '+splited[1];
                    args = new String[splited.length-2];
                    for (int i = 2; i < splited.length; i++) {
                        args[i-2]=splited[i];
                    }
                    return true;
                }
                else if("cd".equals(splited[0])){
                    commandName=splited[0];
                    args = new String[1];
                    args[0] = input.substring(3,input.length());

                    return true;
                }
                else if("rmdir".equals(splited[0])||"touch".equals(splited[0])){
                    commandName=splited[0];
                    args = new String[1];
                    args[0] = input.substring(6,input.length());

                    return true;
                }
                else{
                    commandName=splited[0];
                    args = new String[splited.length-1];
                    for (int i = 1; i < splited.length; i++) {
                        args[i-1]=splited[i];
                    }
                    return true;
                }
            }

            else{
                commandName = splited[0];
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}