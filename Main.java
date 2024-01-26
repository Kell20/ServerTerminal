import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Terminal;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.LineReader;
import org.jline.reader.History;

 import java.io.IOException;

 public class Main{
    public static void main(String[]args)throws IOException{
        ServerTerminal server=new ServerTerminal("CLoudTech Society");
        server.shell();
    }

}

class ServerTerminal{

    private String nameTerminal;
    ServerTerminal(String nameTerminal){
        this.nameTerminal=nameTerminal;
    }

    void shell()throws IOException{
        Terminal terminal=TerminalBuilder.builder()
                .dumb(true)
                .system(true)
                .name(nameTerminal)
                .build();

        LineReader read=LineReaderBuilder.builder()
                .history(new DefaultHistory())
                .terminal(terminal)
                .build();

        while(true){
            String command=read.readLine("eltech#");
            if(command.equalsIgnoreCase("config terminal")){
                // start inner loop config mode
                while(true){
                    String modeConf=read.readLine("eltech(config)#");
                    if(modeConf.split(" ")[0].equals("setIP")){
                        terminal.writer().println("    ipAddress setted was>>"+modeConf.split(" ")[1]);
                    }
                    else if (modeConf.trim().equals("start")) {
                        terminal.writer().println("  server started");
                    }
                    else if(modeConf.equals("exit")){
                        break;
                    }
                    else {
                        terminal.writer().println("   invalid command");
                    }
                }
                // end of inner loop config mode
            }
            else if(command.equals("help")){
                terminal.writer().println("""
                                  <help-commands>
                                  setIP::command for setting ip address of a server:::setIP <IP-ADDRESS>
                                  exit::command for exit a eltech server:::exit
                                  config terminal::command for entering in configuration mode of server:::config
                                  start::command for starting a server::: start
                                  link jline.docx:: command for navigating to jline documentation
                                  </help-commands>
                        """);
            }
            else if (command.equals("exit")) {
                 break;
            }
            else if(command.equals("link jline.docx")){
                terminal.writer().println("https://github.com/jline/jline3");
            }
            else {
                terminal.writer().println("   command is not recognized as internal command or external command");
            }
        }
    }
 }
