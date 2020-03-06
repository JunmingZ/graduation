package com.jim.base.result;

public enum ResponseCode {

	// 公共请求信息
	SUCCESS(200, "请求成功"),
	TABLE_SUCCESS(0, "请求成功"),
	FAIL(500, "操作失败，请联系管理员小姐姐"),
	PARAMETER_MISSING(600,"参数缺失"),
	UNAUTHORIZED(401,"未授权"),
	// ..一真往后面加
	OBJECT_IS_NULL(1000,"对象为空"),
	INSERT_EXCEPTION(1001,"异常插入"),
	DELETE_ID_IS_NULL(1002,"删除的id为空"),
	UPDATE_FAIL(1003,"更新失败"),
	DELETE_FAIL(1004,"删除失败"),

	//信息
	//5000100 - 5000200
	SNO_REPEAT(5000100,"错误操作，该学号已存在！！！"),

	//宿舍
	//5000201 - 5000300
	DORMITORY_REPEAT(5000201,"错误操作，该宿舍已存在！！！"),

	//报修类型
	//5000301 - 5000400
	REPAIR_TYPE_REPEAT(5000301,"错误操作，该报修类型已存在！！！"),

	//报修任务
	//5000401 - 5000500
	REPAIRS_REPEAT(5000401,"错误操作，该报修任务已存在！！！"),

	//维修人员
	//5000501 - 5000600
	REPAIRMAN_UPDATE_FLAG(5000501,"错误操作，更新状态失败！！！"),
	REPAIRMAN_FLAG_NULL(5000502,"维修人的职位状态为空"),
;
	private Integer code;
	
	private String message;
	
	ResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getMessage(String name) {
		for (ResponseCode item : ResponseCode.values()) {
			if (item.name().equals(name)) {
				return item.message;
			}
		}
		return null;
	}

	public static Integer getCode(String name) {
		for (ResponseCode item : ResponseCode.values()) {
			if (item.name().equals(name)) {
				return item.code;
			}
		}
		return null;
	}
}
