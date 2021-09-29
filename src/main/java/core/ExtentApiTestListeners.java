package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import core.assertions.ApiAssertion;
import org.testng.*;

import java.util.Iterator;


public class ExtentApiTestListeners implements ITestListener, ISuiteListener {

    private static final ExtentManager extentManager = new ExtentManager();
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static int totalTestCount;
    private static final String KIND_OF_TEST = "API";


    /**
     * @return Report instance
     */
    public static ExtentReports getRunningReport() {
        return extentReports;
    }

    /**
     * @return Test instance
     */
    public static ExtentTest getInstance() {
        return test.get();
    }

    public static String getKindOfTest() {
        return KIND_OF_TEST;
    }

    ;

    /**
     * Invoked after the test class is instantiated and before
     * any configuration method is called.
     *
     * @param context
     */
    @Override
    public void onStart(ITestContext context) {
        //System.out.println("\nON START TEST");
        getRunningReport().flush();
    }

    /**
     * Invoked after the test class is instantiated and before
     * any configuration method is called.
     */
    @Override
    public void onStart(ISuite suite) {
        //System.out.println("\nON START SUITE");
        setupStartSuite(suite);
        extentReports = new ExtentReports();
        extentReports = extentManager.createInstance();
        extentManager.setApiReportParams();
        getRunningReport().flush();
    }

    public static void setupStartSuite(ISuite suite) {
        ITestNGMethod x;
        for (Iterator var2 = suite.getAllMethods().iterator(); var2.hasNext(); totalTestCount += x.getInvocationCount()) {
            x = (ITestNGMethod) var2.next();
        }
        //System.out.println("Total of tests to be executed: "+totalTestCount+"");
    }


    /**
     * Invoked each time before a test will be invoked.
     * The <code>ITestResult</code> is only partially filled with the references to
     * class, method, start millis and status.
     *
     * @param ITestResult the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    @Override
    public void onTestStart(ITestResult ITestResult) {
        System.out.println("\n\nPARAMETERS:\n"+ITestResult.getParameters()+"");
        //System.out.println("\nON TEST START");
        //Retorna o nome do metodo  = ITestResult.getMethod().getMethodName()
        //Retorna o nome do projeto = ITestResult.getTestContext().getName()
        test.set(extentReports.createTest(ITestResult.getTestContext().getName(), ITestResult.getMethod().getDescription()));
        extentManager.setTestParams(ITestResult, getKindOfTest());
        getRunningReport().flush();
        getInstance();
    }

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass(ApiAssertion.getAssertMessages().toString());
        getRunningReport().flush();
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     */
    @Override
    public void onTestFailure(ITestResult result) {
        //System.out.println("\nON TEST FAIL");
        Throwable testException = result.getThrowable();
        String runtimeError = "Runtime error: <br /><pre>" + testException.getMessage() + "<pre>";
        test.get().fail(runtimeError);
        getRunningReport().flush();
    }

    /**
     * Invoked each time a test is skipped.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SKIP
     */
    @Override
    public void onTestSkipped(ITestResult result) {

    }

    /**
     * Invoked each time a method fails but has been annotated with
     * successPercentage and this failure still keeps it within the
     * success percentage requested.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    /**
     * Invoked after all the tests have run and all their
     * Configuration methods have been called.
     *
     * @param context
     */
    @Override
    public void onFinish(ITestContext context) {
        //System.out.println("\nON TEST FINISH TEST");
        getRunningReport().flush();
    }

    /**
     * This method is invoked after the SuiteRunner has run all
     * the test suites.
     *
     * @param suite
     */
    @Override
    public void onFinish(ISuite suite) {
        //System.out.println("\nON TEST FINISH SUITE");
        getRunningReport().flush();
    }
}
