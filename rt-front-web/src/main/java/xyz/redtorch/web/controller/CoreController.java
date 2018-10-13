package xyz.redtorch.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import xyz.redtorch.core.gateway.GatewaySetting;
import xyz.redtorch.web.annotation.Authorization;
import xyz.redtorch.web.service.CoreEngineWebService;
import xyz.redtorch.web.service.TokenService;
import xyz.redtorch.web.vo.ResultVO;

@Controller
public class CoreController {
	@Autowired
	private CoreEngineWebService coreEngineWebService;
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value = "/sendOrder",method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ResultVO sendOrder(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();
		
		String gatewayID = jsonObject.getString("gatewayID");
		String rtSymbol = jsonObject.getString("rtSymbol");
		double price = jsonObject.getDouble("price");
		int  volume = jsonObject.getInteger("volume");
		String priceType = jsonObject.getString("priceType");
		String direction = jsonObject.getString("direction");
		String offset = jsonObject.getString("offset");
		
		if(StringUtils.isEmpty(gatewayID)
				||StringUtils.isEmpty(rtSymbol)
				||StringUtils.isEmpty(priceType)
				||StringUtils.isEmpty(direction)
				||StringUtils.isEmpty(offset)
				|| volume<=0) {
			result.setResultCode(ResultVO.ERROR);
			result.setMessage("参数不正确");
			return result;
		}
		String orderID = coreEngineWebService.sendOrder(gatewayID, rtSymbol, price, volume, priceType, direction, offset);
		result.setData(orderID);
		
		return result;
	}
	
	@RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO cancelOrder(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();
		
		if(!tokenService.validate(jsonObject.getString("token"))) {
			result.setResultCode(ResultVO.ERROR);
			return result;
		}
		if(StringUtils.isEmpty(jsonObject.getString("rtOrderID"))) {
			result.setResultCode(ResultVO.ERROR);
		}else {
			coreEngineWebService.cancelOrder(jsonObject.getString("rtOrderID"));
		}
		
		return result;
	}
	
	@RequestMapping(value = "/cancelAllOrders",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO cancelAllOrders(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();
		
		if(!tokenService.validate(jsonObject.getString("token"))) {
			result.setResultCode(ResultVO.ERROR);
			return result;
		}
		coreEngineWebService.cancelAllOrders();
		
		return result;
	}
	
	@RequestMapping(value = "/subscribe",method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ResultVO subscribe(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();

		if(!coreEngineWebService.subscribe(jsonObject.getString("rtSymbol"), jsonObject.getString("gatewayID"))) {
			result.setResultCode(ResultVO.ERROR);
		}
		
		return result;
	}
	@RequestMapping(value = "/unsubscribe",method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ResultVO unsubscribe(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();

		if(!coreEngineWebService.unsubscribe(jsonObject.getString("rtSymbol"), jsonObject.getString("gatewayID"))) {
			result.setResultCode(ResultVO.ERROR);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/saveGateway",method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ResultVO saveGateway(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();
		
		GatewaySetting gatewaySetting = jsonObject.toJavaObject(GatewaySetting.class);
		
		coreEngineWebService.saveOrUpdateGatewaySetting(gatewaySetting);
		
		return result;
	}
	
	@RequestMapping("/getGatewaySettings")
	@ResponseBody
	@Authorization
	public ResultVO getGatewaySettings(String token) {

		ResultVO result = new ResultVO();

		
		result.setData(coreEngineWebService.getGatewaySettings());
		
		return result;
	}
	
	@RequestMapping(value = "/deleteGateway",method= RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ResultVO deleteGateway(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();
		
		
		if(!jsonObject.containsKey("gatewayID")) {
			result.setResultCode(ResultVO.ERROR);
			return result;
		}
		
		coreEngineWebService.deleteGateway(jsonObject.getString("gatewayID"));
		
		return result;
	}
	@RequestMapping(value ="/changeGatewayConnectStatus",method= RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ResultVO changeGatewayConnectStatus(@RequestBody JSONObject jsonObject) {

		ResultVO result = new ResultVO();
		
		if(!jsonObject.containsKey("gatewayID")) {
			result.setResultCode(ResultVO.ERROR);
			return result;
		}
		coreEngineWebService.changeGatewayConnectStatus(jsonObject.getString("gatewayID"));
		
		return result;
	}
	
	
	@RequestMapping("/getAccounts")
	@ResponseBody
	@Authorization
	public ResultVO getAccounts(String token) {

		ResultVO result = new ResultVO();

		
		result.setData(coreEngineWebService.getAccounts());
		return result;
	}
	
	@RequestMapping("/getTrades")
	@ResponseBody
	@Authorization
	public ResultVO getTrades(String token) {

		ResultVO result = new ResultVO();

		result.setData(coreEngineWebService.getTrades());
		return result;
	}
	
	@RequestMapping("/getOrders")
	@ResponseBody
	@Authorization
	public ResultVO getOrders(String token) {

		ResultVO result = new ResultVO();


		result.setData(coreEngineWebService.getOrders());
		return result;
	}
	
	@RequestMapping("/getLocalPositionDetails")
	@ResponseBody
	@Authorization
	public ResultVO getLocalPositionDetails(String token) {

		ResultVO result = new ResultVO();

		result.setData(coreEngineWebService.getLocalPositionDetails());
		return result;
	}
	
	@RequestMapping("/getPositions")
	@ResponseBody
	@Authorization
	public ResultVO getPositions(String token) {

		ResultVO result = new ResultVO();


		result.setData(coreEngineWebService.getPositions());
		return result;
	}
	
	
	@RequestMapping("/getContracts")
	@ResponseBody
	@Authorization
	public ResultVO getContracts(String token) {

		ResultVO result = new ResultVO();

		result.setData(coreEngineWebService.getContracts());
		return result;
	}
	
	@RequestMapping("/getLogs")
	@ResponseBody
	@Authorization
	public ResultVO getLogs(String token) {

		ResultVO result = new ResultVO();

		result.setData(coreEngineWebService.getLogDatas());
		return result;
	}
	
}
