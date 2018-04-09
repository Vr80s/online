
package com.xczhihui.course.vo;
/**
 * 更新节点顺序vo
 * 
 * @author www
 *
 */
public class LibraryVo {

	private String id;

	private String targetId;

	private String targetPId;

	private String moveType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getTargetPId() {
		return targetPId;
	}

	public void setTargetPId(String targetPId) {
		this.targetPId = targetPId;
	}

}
