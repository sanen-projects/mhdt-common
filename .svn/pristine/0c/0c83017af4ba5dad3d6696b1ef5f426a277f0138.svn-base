package com.mhdt.test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.mhdt.Print;
import com.mhdt.net.SimpleDownload;
import com.mhdt.net.Urls;
import com.mhdt.toolkit.FileUtility.FileType;

public class FileDownTest {

	public static void main(String[] args) throws Exception {

		String url = "http://download.sanen.online/static/update/ecaj/3.0.0/template/README.MD";
		Urls.downLoad(url, new File("C:\\Users\\Administrator\\Desktop"),FileType.Directory,new SimpleDownload());
		Print.info("下载完毕");
		
		try {
			Urls.downLoad("", new File(""), FileType.File, ( HttpURLConnection conn) -> {
				conn.setRequestProperty("Referer", "");
				conn.setDoOutput(true);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
