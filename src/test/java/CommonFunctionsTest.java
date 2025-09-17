
import org.apache.poi.ss.usermodel.Sheet;
import org.example.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class CommonFunctionsTest {

    CommonFunctions commonFunctions =new CommonFunctions();
    String sampleFilePath="Sample Data.xlsx";

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
    public void markTypesBlocksWrite() throws IOException {
        ObjectFile of = new ObjectFile(sampleFilePath);
        Sheet sampleSheet = of.getSheets().get(0);
        ObjectSheet os = new ObjectSheet(sampleSheet);
        ArrayList<ObjectBlock> blocks = os.getSheetBlocks();
        int counter=0;
        for (ObjectBlock block : blocks) {
            boolean ew =  ExcelWriter.writeExcelFileFrom2DArray("test"+counter++ +".xlsx",block.getBlockGrid());
        }


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
