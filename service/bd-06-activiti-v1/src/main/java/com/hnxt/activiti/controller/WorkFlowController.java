package com.hnxt.activiti.controller;

import com.hnxt.activiti.service.WorkFlowService;
import com.hnxt.activiti.vo.WorkFlowVo;
import com.hnxt.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yinz
 * @Date 2021/11/24 - 15:17
 */
@RestController
@RequestMapping("/workFlow")
public class WorkFlowController {

	@Autowired
	private WorkFlowService workFlowService;


	/**
	 * 加载部署信息数据
	 */
	@PostMapping("/loadAllDeployment")
	public R loadAllDeployment(WorkFlowVo workFlowVo){
		return R.ok().data("deployment", workFlowService.loadAllDeployment(workFlowVo));
	}

	/**
	 * 添加流程部署
	 */
	@PostMapping("/addWorkFlow")
	public R addWorkFlow(MultipartFile mf, WorkFlowVo workFlowVo) throws IOException {
		this.workFlowService.addWorkFlow(mf.getInputStream(),workFlowVo.getDeploymentName());
		return R.ok();
	}
}
