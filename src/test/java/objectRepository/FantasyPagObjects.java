package objectRepository;

import org.openqa.selenium.By;

public class FantasyPagObjects {

    public By viewLeaderBoard = By.xpath("(//*[contains(text(),'View Leaderboard')])[3]");
    public By teamNames = By.xpath(" //*[@class=\"df-plyrSel__name\"]");
    public By overAllTab = By.xpath("//*[normalize-space()='OVERALL']");
    public By closeToGoToTeamsList = By.xpath("//*[@class='dfi-close']");
    public By transfersCount = By.xpath("//*[@class='df-transfer__overall']");

    public By transfersLeft = By.xpath("//*[@class='df-transfer__head df-transfer__head--rowCol']/span[3]/em");

    public By totalPoints = By.xpath("//*[@class='df-transfer__head df-transfer__head--rowCol']/span[2]/em");


    public By boosterCount = By.xpath("//*[@class='df-pitch__booster ']");
    public By scoreList = By.xpath("//*[@class=\"df-contest__pitch-num\"]");
    public By createTeamBtn = By.xpath("//*[@class='df-btn df-btn__primary']");
    public By backBtn = By.xpath("//*[@class='icon-arrow_back cursor-pointer text-2xl']");
    public By myContests = By.xpath(" //*[contains(text(), 'MY C')]");

    public By teamNameRow = By.xpath("//*[@class=\"df-tbl__row\"]");
    public By score = By.xpath("//*[@class='df-tbl__cell df-tbl__cell--pts']");

    public By teamNameDetails = By.xpath("//div[@class='df-overlay__title']/span");


    //*[contains(text(), 'MY C')]
////*[@class='df-contest__cta']

}
