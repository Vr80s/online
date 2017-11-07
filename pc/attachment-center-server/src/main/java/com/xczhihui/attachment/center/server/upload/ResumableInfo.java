package com.xczhihui.attachment.center.server.upload;

import java.io.File;
import java.util.HashSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*   resumableChunkNumber: The index of the chunk in the current upload. First chunk is 1 (no base–0 counting here).
*	resumableChunkSize: The general chunk size. Using this value and resumableTotalSize you can calculate the total number of chunks. Please note that the size of the data received in the HTTP might be lower than resumableChunkSize of this for the last chunk for a file.
*	resumableTotalSize: The total file size.
*	resumableIdentifier: A unique identifier for the file contained in the request.
*	resumableFilename: The original file name (since a bug in Firefox results in the file name not being transmitted in chunk multipart posts).
*	resumableRelativePath: The file’s relative path when selecting a directory (defaults to file name in all browsers except Chrome).
 * by fanxu
 */
public class ResumableInfo {

    public int      resumableChunkSize;// 
    public long     resumableTotalSize;//
    public String   resumableIdentifier;//
    public String   resumableFilename;//
    public String   resumableRelativePath;//
    public String   storeName;//文件的存储名称
    public String   createUserId;//用户id
    public String   fileType;//文件类型，1==图片，2==附件，3其他=错误
    public String   projectName;// 项目名称
    public String   storePath;//存放路径
    
    public static class ResumableChunkNumber {
        public ResumableChunkNumber(int number) {
            this.number = number;
        }

        public int number;

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ResumableChunkNumber
                    ? ((ResumableChunkNumber)obj).number == this.number : false;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }

    //Chunks uploaded
    public HashSet<ResumableChunkNumber> uploadedChunks = new HashSet<ResumableChunkNumber>();

    public String resumableFilePath;

    public boolean vaild(){
        if (resumableChunkSize < 0 || resumableTotalSize < 0
                || HttpUtils.isEmpty(resumableIdentifier)
                || HttpUtils.isEmpty(resumableFilename)
                || HttpUtils.isEmpty(resumableRelativePath)) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean checkIfUploadFinished(String storeName) {
        //check if upload finishedr
        int count = (int) Math.ceil(((double) resumableTotalSize) / ((double) resumableChunkSize));
        for(int i = 1; i < count; i ++) {
            if (!uploadedChunks.contains(new ResumableChunkNumber(i))) {
                return false;
            }
        }

        //Upload finished, change filename.
        File file = new File(resumableFilePath);
//        String fileParh = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("/"));
//        String new_path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - ".temp".length());
        String new_path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
        file.renameTo(new File(new_path +File.separator+ storeName));
        return true;
    }
    
    
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getResumableChunkSize() {
		return resumableChunkSize;
	}
	public void setResumableChunkSize(int resumableChunkSize) {
		this.resumableChunkSize = resumableChunkSize;
	}
	public long getResumableTotalSize() {
		return resumableTotalSize;
	}
	public void setResumableTotalSize(long resumableTotalSize) {
		this.resumableTotalSize = resumableTotalSize;
	}
	public String getResumableIdentifier() {
		return resumableIdentifier;
	}
	public void setResumableIdentifier(String resumableIdentifier) {
		this.resumableIdentifier = resumableIdentifier;
	}
	public String getResumableFilename() {
		return resumableFilename;
	}
	public void setResumableFilename(String resumableFilename) {
		this.resumableFilename = resumableFilename;
	}
	public String getResumableRelativePath() {
		return resumableRelativePath;
	}
	public void setResumableRelativePath(String resumableRelativePath) {
		this.resumableRelativePath = resumableRelativePath;
	}
	public HashSet<ResumableChunkNumber> getUploadedChunks() {
		return uploadedChunks;
	}
	public void setUploadedChunks(HashSet<ResumableChunkNumber> uploadedChunks) {
		this.uploadedChunks = uploadedChunks;
	}
	public String getResumableFilePath() {
		return resumableFilePath;
	}
	public void setResumableFilePath(String resumableFilePath) {
		this.resumableFilePath = resumableFilePath;
	}
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
    
    
    
}
