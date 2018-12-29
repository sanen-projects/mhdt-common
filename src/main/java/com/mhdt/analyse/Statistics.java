package com.mhdt.analyse;

import java.io.File;

import com.mhdt.analyse.Validate;
import com.mhdt.io.FileIO;
import com.mhdt.toolkit.Reflect;
import com.mhdt.toolkit.StringUtility;

/**
 * A functional class by Statistical ,At present is still in the stage
 * 
 * @author 懒得出风头<br>
 *         Date: 2016/07/20 <br>
 *         Time： 13:59
 */
public class Statistics {

	private Statistics() {
	}

	/**
	 * Statistics chinese counts in content.
	 * @param content
	 * @return
	 */
	public static int chineseCount(String content) {
		char[] chars = content.toCharArray();
		int count = 0;
		for (char c : chars) {
			if (Validate.isChinese(c))
				count++;
		}
		return count;
	}

	/**
	 * Statistic english counts in content.
	 * 
	 * @param content
	 * @return count
	 */
	public static int englishCount(String content) {
		char[] chars = content.toCharArray();
		int count = 0;
		for (char c : chars) {
			if (Validate.isEnglish(c))
				count++;
		}
		return count;
	}

	/**
	 * Statistics blank count in content.
	 * @param content
	 * @return
	 */
	public static int blankCount(String content) {
		char[] chars = content.toCharArray();
		int count = 0;
		for (char c : chars) {
			if (Validate.isBlank(c))
				count++;
		}
		return count;
	}

	/**
	 * 统计代码行数
	 * @param projectFolder - 项目文件夹
	 * @param fileSuffix - 文件后缀
	 * @return
	 */
	public static PI countLines(File projectFolder, String fileSuffix) {

		PI projectInfo = new PI(projectFolder);
		try {
			for (File temp : projectFolder.listFiles()) {
				if (temp.isFile()) {

					if (temp.getName().endsWith(fileSuffix)) {
						projectInfo.effective_count += caculate(temp);
						projectInfo.lines_count += FileIO.getContent(temp).split("\n").length;
						projectInfo.files_count += 1;
					}

				} else if (temp.isDirectory()) {
					PI tempInfo = countLines(temp, fileSuffix);
					projectInfo.effective_count += tempInfo.effective_count;
					projectInfo.lines_count += tempInfo.lines_count;
					projectInfo.files_count += tempInfo.files_count;
				}
			} 
		}catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage()+"	"+projectFolder.getAbsolutePath());
		}
			

		return projectInfo;
	}

	private static int caculate(File file) {
		int count = 0;
		String[] arrays = FileIO.getContent(file).split("\n");
		for (String temp : arrays) {

			temp = StringUtility.removeBlankChar(temp);

			if (Validate.isNullOrEmpty(temp) || temp.startsWith("package") || temp.startsWith("import")
					|| temp.length() < 3)
				continue;

			count += 1;
		}
		return count;
	}

	public static class PI {

		public PI(File projectFolder) {
			this.project_name = projectFolder.getName()
					.substring(projectFolder.getName().lastIndexOf(File.separator) + 1);
		}

		public int lines_count;
		public int effective_count;
		public int files_count;
		public String project_name;

		@Override
		public String toString() {
			return "PI[" +"项目名：" + project_name +","
					+ " 	文件数：" + files_count +","
					+ " 	总行数：" + lines_count+"," 
					+ "		有效行数：" + effective_count + "]";
		}

	}

	@SafeVarargs
	public static <T> double sum(String column, T... array) throws Exception {
		double result = 0;
		
		for(T t : array) {
			Object object = Reflect.getValue(t, column);
			
			if(object == null || !Validate.isNumber(object))
				continue;
			
			result+= Double.parseDouble(object.toString());
		}
		
		return result;
	}

}
