import com.urmanicrobert.sitovaAnalyza.frame.BaseFrame;
import com.urmanicrobert.sitovaAnalyza.frame.MainFrame;

public class Main {
    public static void main(String[] args) {
        Thread threadFrame = new Thread(new MainFrame());
        threadFrame.start();
    }
    // BaseFrame newFrame = new BaseFrame();
    // newFrame.createAndShowGUI();
}
