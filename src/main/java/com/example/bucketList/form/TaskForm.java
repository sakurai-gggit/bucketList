package com.example.bucketList.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskForm {
	@Builder.Default
	private String order = "default";
}