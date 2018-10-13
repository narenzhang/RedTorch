package xyz.redtorch.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.redtorch.web.service.TokenService;
import xyz.redtorch.web.vo.ResultVO;

@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	
	@RequestMapping("/getToken")
	@ResponseBody
	public ResultVO getToken(String username, String password) {

		ResultVO result = new ResultVO();
		String token = tokenService.login(username, password);
		if(StringUtils.isEmpty(token)) {
			result.setResultCode(ResultVO.ERROR);
		}else {
			result.setData(token);
		}
		return result;
	}
	
	@RequestMapping("/tokenValidate")
	@ResponseBody
	public ResultVO tokenValidate(String token) {

		ResultVO result = new ResultVO();
		
		if(!tokenService.validate(token)) {
			result.setResultCode(ResultVO.ERROR);
		}
		
		return result;
	}
	
}
