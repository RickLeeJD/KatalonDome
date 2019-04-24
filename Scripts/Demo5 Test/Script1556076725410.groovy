import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

//開啟瀏覽器
WebUI.openBrowser('')

//進入URL
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

//等待btn_MakeAppointment物件(可點擊狀態)
WebUI.waitForElementClickable(findTestObject('Login/btn_MakeAppointment'), 10)

//點擊btn_MakeAppointment物件
WebUI.click(findTestObject('Login/btn_MakeAppointment'))

//--------  Login Star----------------

//等待button_Login物件(可點擊狀態)
WebUI.waitForElementClickable(findTestObject('Login/button_Login'), 10)

//輸入使用者名稱
WebUI.setText(findTestObject('Login/input__Username'), findTestData('demotest').getValue('UserName', 1))
String UserName = WebUI.getAttribute(findTestObject('Login/input__Username'), 'value')
WebUI.verifyMatch(UserName, findTestData('demotest').getValue('UserName', 1) , false)

//輸入使用者密碼
WebUI.setText(findTestObject('Login/input__Password'), findTestData('demotest').getValue('Password', 1))
String Password = WebUI.getAttribute(findTestObject('Login/input__Password'), 'value')
WebUI.verifyMatch(Password, findTestData('demotest').getValue('Password', 1) , false)

//點擊登入
WebUI.click(findTestObject('Login/button_Login'))

//--------  Login End----------------


//--------  Input Star----------------

//等待物件select_Facility(下拉式選單存在)
WebUI.waitForElementPresent(findTestObject('Input/select_Facility'), 10)

//判斷下拉式選單內是否有3個選項
if (WebUI.getNumberOfTotalOption(findTestObject('Input/select_Facility')) == 3) {
    
	//比對選項內容是否正確
    WebUI.verifyOptionsPresent(findTestObject('Input/select_Facility'), ['Tokyo CURA Healthcare Center'
            , 'Hongkong CURA Healthcare Center', 'Seoul CURA Healthcare Center'])

    //選擇項目
    WebUI.selectOptionByValue(findTestObject('Input/select_Facility'), findTestData(
            'demotest').getValue('Facility', 1), false)

    //驗證是否選擇正確項目
    WebUI.verifyOptionSelectedByValue(findTestObject('Input/select_Facility'), 
        findTestData('demotest').getValue('Facility', 1), false, 10)
}

//若輸入資料ApplyForHospital欄位為Y，勾選Apply For Hospital(checkbox)，並檢驗是否勾選
if (findTestData('demotest').getValue('ApplyForHospital', 1) == 'Yes') {
    WebUI.check(findTestObject('Input/input_ApplyForHospital'))
	WebUI.verifyElementChecked(findTestObject('Input/input_ApplyForHospital'), 10)
}
else{
	WebUI.uncheck(findTestObject('Input/input_ApplyForHospital'))
	WebUI.verifyElementNotChecked(findTestObject('Input/input_ApplyForHospital'), 10)
}

//判斷輸入資料HealthcareProgram欄位之值
switch (findTestData('demotest').getValue('HealthcareProgram', 1)) {
    //若為Medicare，則點擊Medicare(radio btn)
	case 'Medicare':
        WebUI.check(findTestObject('Input/input_Medicare_programs'))
		WebUI.verifyElementChecked(findTestObject('Input/input_Medicare_programs'), 10)
		break
	//若為Medicaid，則點擊Medicaid(radio btn)
    case 'Medicaid':
        WebUI.check(findTestObject('Input/input_Medicaid_programs'))
		WebUI.verifyElementChecked(findTestObject('Input/input_Medicaid_programs'), 10)
		break
	//若為None，則點擊None(radio btn)
    case 'None':
        WebUI.check(findTestObject('Input/input__None_Programs'))
		WebUI.verifyElementChecked(findTestObject('Input/input__None_Programs'), 10)
		break
}

//輸入日期
WebUI.setText(findTestObject('Input/input__VisitDate'), findTestData('demotest').getValue(
        'Date', 1))

//輸入日期後，會面上會有日曆工具視窗，為避免影響執行，點擊下方Comment欄位使日曆消失
WebUI.click(findTestObject('Input/input__textComment'))

//比對日期是否輸入正確
String date = WebUI.getAttribute(findTestObject('Input/input__VisitDate'), 'value')
WebUI.verifyMatch(date, findTestData('demotest').getValue('Date', 1) , false)

//輸入Comment資料
WebUI.setText(findTestObject('Input/input__textComment'), findTestData('demotest').getValue(
        'Comment', 1))

//比對是否正確輸入
String Comment = WebUI.getAttribute(findTestObject('Input/input__textComment'), 'value')
WebUI.verifyMatch(Comment, findTestData('demotest').getValue('Comment', 1) , false)

//預約按鈕是否可點擊
WebUI.verifyElementClickable(findTestObject('Input/button_Book Appointment'))
//點選預約按鈕
WebUI.click(findTestObject('Input/button_Book Appointment'))

//--------  Input End----------------

//--------  Verify Start----------------

//等待物件存在
WebUI.waitForElementPresent(findTestObject('Verify/verify__Facility'), 10)

//比對Facility欄位是否與輸入相同
WebUI.verifyElementText(findTestObject('Verify/verify__Facility'), findTestData(
        'demotest').getValue('Facility', 1))

//Data ApplyForHospital欄位若為Y，比對Apply For Hospital Readmission是否為YES，否則NO
if (findTestData('demotest').getValue('ApplyForHospital', 1) == 'Yes') {
    WebUI.verifyElementText(findTestObject('Verify/verify__ApplyForHospitalReadmission'), 
        'Yes')
} else {
    WebUI.verifyElementText(findTestObject('Verify/verify__ApplyForHospitalReadmission'), 
        'No')
}

//比對Healthcare Program欄位是否與輸入相同
WebUI.verifyElementText(findTestObject('Verify/verify__HealthcareProgram'), findTestData(
        'demotest').getValue('HealthcareProgram', 1))

//比對date欄位是否與輸入相同
WebUI.verifyElementText(findTestObject('Verify/verify__date'), findTestData('demotest').getValue(
        'Date', 1))

//比對comment是否與輸入相同
WebUI.verifyElementText(findTestObject('Verify/verify__comment'), findTestData('demotest').getValue(
        'Comment', 1))

//點選回首頁
WebUI.click(findTestObject('Input/btn_Go to Homepage'))

//--------  Verify End----------------

//等待btn_MakeAppointment物件(可點擊狀態)
WebUI.waitForElementClickable(findTestObject('Login/btn_MakeAppointment'), 10)

