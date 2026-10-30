package com.hexagon.springmvc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrPosition {
	private int hrPositionId;
	private String positionCode;
	private String positionName;
	private int isActive;
}
