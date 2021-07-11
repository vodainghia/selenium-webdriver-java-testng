package testng;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportListener implements ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
		System.out.println("Làm gì đó sau khi Class này được chạy xong DONE");
	}

	@Override
	public void onStart(ITestContext arg0) {
		System.out.println("Làm gì đó trước khi Class này được chạy");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// Hiếm dùng
		System.out.println("Làm gì đó sau khi TC này fail trong khoảng bao nhiêu % steps");
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		System.out.println("Làm gì đó khi TC này failed");
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		System.out.println("Làm gì đó khi TC này skip");
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		System.out.println("Làm gì đó khi TC này được chạy");
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		System.out.println("Làm gì đó khi TC này pass DONE");
	}

}
