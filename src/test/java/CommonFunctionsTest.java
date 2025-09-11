
import org.apache.poi.ss.usermodel.Sheet;
import org.example.ObjectCell;
import org.example.ObjectFile;
import org.example.ObjectSheet;
import org.junit.jupiter.api.Test;
import org.example.CommonFunctions;

import java.io.IOException;

public class CommonFunctionsTest {

    CommonFunctions commonFunctions =new CommonFunctions();
    String sampleFilePath="C:\\Users\\camer\\OneDrive\\Documents\\Repos\\Speed_Sheets\\SpeedSheets\\Sample Data.xlsx";

    @Test
    public void copyFile() throws IOException {
        commonFunctions.copyFile(sampleFilePath);
    }

    @Test
    public void markTypes() throws IOException {
        ObjectFile of = new ObjectFile(sampleFilePath);
        Sheet sampleSheet = of.getSheets().get(0);
        ObjectSheet os = new ObjectSheet(sampleSheet);

    }



    @Test
    public void markUsage() throws IOException {
        commonFunctions.copyFile("Sample Data.xlsx");
    }

    @Test
    public void testreadExcelFile() throws IOException {
        commonFunctions.readExcelFile("Sample Data.xlsx");
    }




}
