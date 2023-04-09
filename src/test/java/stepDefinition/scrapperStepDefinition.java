package stepDefinition;

import helper.SessionManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jxl.write.*;
import objectRepository.BasePage;
import objectRepository.FantasyPagObjects;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class scrapperStepDefinition extends BasePage {
    FantasyPagObjects fpo = new FantasyPagObjects();

    @Given("Launch fantasy Page")
    public void launchPage() throws InterruptedException {

        driver.navigate().to("https://fantasy.iplt20.com/season/contest");

        SessionManager sessionManager = new SessionManager(driver);
        JSONObject existingSession = new JSONObject();
        existingSession.put("path", "/");
        existingSession.put("domain", "fantasy.iplt20.com");
        existingSession.put("name", "connector.sid");
        existingSession.put("isHttpOnly", true);
        existingSession.put("isSecure", true);
        existingSession.put("expiry", "2023-07-01T21:25:37.254Z");
        existingSession.put("value", "s:EQe5XWjoiU6bYjj00lnCzELBoq0EvKY5.7Dhmk//udXBFJLwh+7XDhy0J//y24s9ZmkwjLOiSrhc");


        sessionManager.setCookies(existingSession);
        driver.navigate().refresh();
        Thread.sleep(1000);

    }


    @When("user clicks on Create Team")
    public void user_clicks_on_create_team() throws Exception {
        driver.findElement(By.xpath("//*[@class='df-btn df-btn__primary']")).click();
    }

    @When("Clicks on back button")
    public void clicks_on_back_button() throws Exception {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(fpo.backBtn).click();
    }

    @Then("Clicks on My Contests")
    public void clicks_on_my_contests() throws Exception {
        driver.findElement(fpo.myContests).click();
    }

    @Then("Clicks on ViewLeaderboard")
    public void clicks_on_view_leaderboard() throws Exception {
        WebElement Element = driver.findElement(fpo.viewLeaderBoard);
        scrollToElement(Element, 500);
        Element.click();
    }

    @Then("User should be able to view all participating teams")
    public void user_should_be_able_to_view_all_participating_teams() throws Exception {
        System.out.println("====================================");
        List<WebElement> teamList = driver.findElements(fpo.teamNames);
        List<WebElement> scoreList = driver.findElements(fpo.score);
    }


    @Then("User clicks on each team to get score in each match and Booster info and C VC info")
    public void user_clicks_on_each_team_to_get_score_in_each_match_and_booster_info_and_c_vc_info() throws InterruptedException, WriteException, IOException {
        List<WebElement> teamNameRow = driver.findElements(fpo.teamNameRow);
        Map<String, ArrayList<String>> multiValueMap = new HashMap<String, ArrayList<String>>();
        String teamName;
        int i=0;
        for (WebElement team : teamNameRow) {
            //to skip the 1st row
            if(i==0){
                i=i+1;
            }
            else{
                try {
                    i=i+1;
                   if (i%3==0){
                       scrollNotUsingJavaScript();
                   }
                    team.click();
                    teamName=driver.findElement(fpo.teamNameDetails).getText();
                    multiValueMap.put(teamName, new ArrayList<String>());
                    Thread.sleep(500);
                    WebElement overAllTab=driver.findElement(fpo.overAllTab);
                    overAllTab.click();
                    multiValueMap.get(teamName).add(teamName);
                    String totalPoints=driver.findElement(fpo.totalPoints).getText().trim();
                    multiValueMap.get(teamName).add(driver.findElement(fpo.totalPoints).getText());
                    multiValueMap.get(teamName).add(String.valueOf(i-1));
                    multiValueMap.get(teamName).add(driver.findElement(fpo.transfersLeft).getText());
                    double txUsed=150-Integer.parseInt(driver.findElement(fpo.transfersLeft).getText().trim());
                    multiValueMap.get(teamName).add(String.valueOf(txUsed));
                }
                catch(Exception e)
                {

                    scrollToElement(team, 1000);
                    team.click();
                    teamName=driver.findElement(fpo.teamNameDetails).getText();
                    Thread.sleep(500);
                    WebElement overAllTab=driver.findElement(fpo.overAllTab);
                    overAllTab.click();
                    try {
                        multiValueMap.get(teamName).add(teamName);
                        String totalPoints=driver.findElement(fpo.totalPoints).getText().trim();
                        multiValueMap.get(teamName).add(driver.findElement(fpo.totalPoints).getText());
                        multiValueMap.get(teamName).add(String.valueOf(i-1));
                        multiValueMap.get(teamName).add(driver.findElement(fpo.transfersLeft).getText());
                        double txUsed=150-Integer.parseInt(driver.findElement(fpo.transfersLeft).getText().trim());
                        multiValueMap.get(teamName).add(String.valueOf(txUsed));
                    }catch(Exception e1){
                        System.out.println("exception");
                }

                }

           }

        }

        writeToExcel(multiValueMap);


    }

    public void scrollToElement(WebElement element, long sleepTime) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //This will scroll the page till the element is found
        js.executeScript("arguments[0].scrollIntoView();", element);
        Thread.sleep(sleepTime);
    }

    public void scrollByPixel(long pixel, long sleepTime) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(90,pixel)","");
        Thread.sleep(sleepTime);
    }

    public void scrollNotUsingJavaScript(){
        new Actions(driver)
                .scrollByAmount(0, 100)
                .perform();
    }

    public void writeToExcel(Map<String, ArrayList<String>> multiValueMap ) throws WriteException, IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Lazy Genius");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Team Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Points");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Points Rank");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Transfers Left");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Transfers Used");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Boosters Used");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Transfers Efficiency");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Efficiency Rank");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Lag by Points");
        headerCell.setCellStyle(headerStyle);



        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        List<WebElement> teamList = driver.findElements(fpo.teamNames);
        int i=1;

        for (WebElement team : teamList) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 5; j++) {
            try {

                Cell cell = row.createCell(j);
                cell.setCellValue(multiValueMap.get(team.getText()).get(j));
                cell.setCellStyle(style);
            }catch (Exception e){
                System.out.println("Exception while inserting data to excel");
            }

            }
            i=i+1;

        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "lazyGenius.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }


}
