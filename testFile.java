package com.eebbk.app.store.operation.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eebbk.app.store.operation.dto.GetImgAggregationDto;
import com.eebbk.app.store.operation.enums.imgFeatureEnums.ImgFeatureStateEnum;
import com.eebbk.app.store.operation.pojo.HomeMiddleBannerV5Pojo;
import com.eebbk.app.store.operation.service.ImgAggregationService;
import com.eebbk.app.store.operation.service.ImgFeatureService;
import com.eebbk.app.store.operation.service.UserInfoService;
import com.eebbk.app.store.operation.util.EnumToListUtil;
import com.eebbk.app.store.operation.util.ToolUtil;
import com.eebbk.app.store.operation.vo.AddImgAggregationVo;
import com.eebbk.app.store.operation.vo.ConstantEnumObject;
import com.eebbk.app.store.operation.vo.DeleteImgAggregationVo;
import com.eebbk.app.store.operation.vo.DownImgAggregationVo;
import com.eebbk.app.store.operation.vo.RequestVo;
import com.eebbk.app.store.operation.vo.UpImgAggregationVo;
import com.eebbk.app.store.operation.vo.UpdateImgAggregationVo;
import com.eebbk.app.store.operation.vo.UserInfo;
import com.eebbk.app.store.operation.vo.imgFeature.AddImgFeatureVo;
import com.eebbk.app.store.operation.vo.imgFeature.CheckImgFeaturePublishAccessVo;
import com.eebbk.app.store.operation.vo.imgFeature.GetImgFeatureVo;
import com.eebbk.app.store.operation.vo.imgFeature.UpdateImgFeatureVisibilityVo;
import com.eebbk.app.store.operation.vo.imgFeature.UpdateImgFeatureVo;
import com.eebbk.common.exception.ResponseTemplate;
import com.eebbk.common.json.JsonTool;
import com.eebbk.common.log.LogUtil;
import com.eebbk.common.network.HttpStatusCode;
import com.eebbk.common.vo.ResultVo;

import io.swagger.annotations.ApiOperation;

/**
 * 【精选 --> 图片专题】接口层，适配v4.6.0以上
 *
 * @author 孙泽宇
 * @date 2018/11/29 10:37
 * @company 步步高教育电子有限公司
 */
@Controller
@RequestMapping("/admin/choicenessPage/imgFeature")
public class ImgFeatureController
{
    @Autowired
    ImgFeatureService imgFeatureService;
    @Autowired
    ImgAggregationService imgAggregationService;
    

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value = "精选 --> 图片专题 --> 底部固定位置)", notes = "获取底部固定位置列表", response = ResultVo.class)
    @RequestMapping(value = "/getImgFeatureList", method = { RequestMethod.POST })
    public void getImgFeatureList(@RequestBody RequestVo<GetImgFeatureVo> models, HttpServletRequest request,
            HttpServletResponse response)
    {
        Map<String, Object> specialList = imgFeatureService.getImgFeatureList(models.getObject());
        ResponseTemplate.outputCn(specialList, HttpStatusCode.SUCCESS, response);
    }

    @ApiOperation(value = "精选 --> 图片专题 --> 底部固定位置", notes = "底部固定位置上架或设置预约下架时间", response = com.eebbk.app.store.operation.vo.ResultVo.class)
    @RequestMapping(value = "/setReservationTime", method = { RequestMethod.POST })
    public void setReservationTime(@RequestBody RequestVo<HomeMiddleBannerV5Pojo> models, HttpServletRequest request,
            HttpServletResponse response)
    {
        String userName = getUserName(request, response);
        if (userName == null)
            return;
        ResponseTemplate.outputCn(imgFeatureService.setReservationExpireTime(models.getObject(), userName),
                HttpStatusCode.SUCCESS, response);
    }

    @ApiOperation(value = "精选 --> 图片专题 --> 底部固定位置", notes = "底部固定位置下架", response = com.eebbk.common.vo.ResultVo.class)
    @RequestMapping(value = "/updateImgFeatureVisibility", method = { RequestMethod.POST })
    public void updateImgFeatureVisibility(@RequestBody UpdateImgFeatureVisibilityVo updateImgFeatureVisibilityVo,
            HttpServletRequest request, HttpServletResponse response)
    {
        String userName = getUserName(request, response);
        if (userName == null)
            return;
        ResponseTemplate.outputCn(imgFeatureService.updateImgFeatureVisibility(updateImgFeatureVisibilityVo, userName),
                HttpStatusCode.SUCCESS, response);
    }

    @ApiOperation(value = "精选 --> 图片专题 --> 底部固定位置", notes = "获取状态枚举")
    @RequestMapping(value = "/getState", method = { RequestMethod.GET })
    public void getState(HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        List<ConstantEnumObject> stateList = EnumToListUtil.getEnumListByEnumClass(ImgFeatureStateEnum.class);
        ResponseTemplate.outputCn(stateList, HttpStatusCode.SUCCESS, response);
    }

    @ApiOperation(value = "精选 --> 图片专题 --> 底部固定位置", notes = "添加底部固定位置")
    @RequestMapping(value = "/addImgFeature", method = { RequestMethod.POST })
    public void addImgFeature(@RequestBody AddImgFeatureVo addImgFeatureVo, HttpServletRequest request,
            HttpServletResponse response)
    {
        String userName = getUserName(request, response);
        if (userName == null)
            return;
        ResponseTemplate.outputCn(imgFeatureService.addImgFeature(addImgFeatureVo, userName), response);
    }

    @ApiOperation(value = "精选 --> 图片专题 --> 底部固定位置", notes = "修改底部固定位置")
    @RequestMapping(value = "/updateImgFeature", method = { RequestMethod.POST })
    public void updateImgFeature(@RequestBody UpdateImgFeatureVo updateImgFeatureVo, HttpServletRequest request,
            HttpServletResponse response)
    {
        String userName = getUserName(request, response);
        if (userName == null)
            return;
        ResponseTemplate.outputCn(imgFeatureService.updateImgFeature(updateImgFeatureVo, userName), response);
    }
    
    @ApiOperation(value = "精选 --> 图片专题 --> 图片专题聚合引用)", notes = "获取图片专题聚合引用列表", response = ResultVo.class)
    @RequestMapping(value = "/getImgAggregationregationList", method = { RequestMethod.POST })
    public void getImgAggregationregationList(@RequestBody GetImgAggregationDto getImgAgg, HttpServletRequest request,
            HttpServletResponse response)
    {
        LogUtil.info("接收请求:/getImgAggregationregationList，params：" + JsonTool.toJson(getImgAgg));
        Map<String, Object> specialList = imgAggregationService.getImgAggregationList(getImgAgg);
        ResponseTemplate.outputCn(specialList, HttpStatusCode.SUCCESS, response);
        LogUtil.info("完成请求:/getImgAggregationregationList，result：" + JsonTool.toJson(specialList));
    }
    
    @ApiOperation(value = "精选 --> 图片专题 --> 图片专题聚合引用", notes = "添加图片专题聚合引用", response = ResultVo.class)
    @RequestMapping(value = "/addImgAggregationregationregation", method = { RequestMethod.POST })
    public void addImgAggregationregation(@RequestBody AddImgAggregationVo addImgAggVo, HttpServletRequest request,
            HttpServletResponse response)
    {
        LogUtil.info("接收请求:/addImgAggregationregationregation，params：" + JsonTool.toJson(addImgAggVo));
        String userName = getUserName(request, response);
        if (userName == null)
            ResponseTemplate.outputCn("用户信息字段无效", HttpStatusCode.PARAMETER_USERINFO_INVALID, response);;
        ResponseTemplate.outputCn(imgAggregationService.addImgAggregation(addImgAggVo, userName), response);
        LogUtil.info("完成请求:/addImgAggregationregationregation");
    }
    
    @ApiOperation(value = "精选 --> 图片专题 --> 图片专题聚合引用", notes = "修改图片专题聚合引用", response = ResultVo.class)
    @RequestMapping(value = "/updateImgAggregationregation", method = { RequestMethod.POST })
    public void updateImgAggregationregation(@RequestBody UpdateImgAggregationVo updateImgAggVo, HttpServletRequest request,
            HttpServletResponse response)
    {
        LogUtil.info("接收请求:/updateImgAggregationregation，params：" + JsonTool.toJson(updateImgAggVo));
        String userName = getUserName(request, response);
        if (userName == null)
            ResponseTemplate.outputCn("用户信息字段无效", HttpStatusCode.PARAMETER_USERINFO_INVALID, response);
        ResponseTemplate.outputCn(imgAggregationService.updateImgAggregation(updateImgAggVo, userName), response);
        LogUtil.info("完成请求:/updateImgAggregationregation");
    }
    
    @ApiOperation(value = "精选 --> 图片专题 --> 图片专题聚合引用", notes = "删除图片专题聚合引用", response = ResultVo.class)
    @RequestMapping(value = "/deleteImgAggregation", method = { RequestMethod.POST })
    public void deleteImgAggregation(@RequestBody DeleteImgAggregationVo deleteImgAggVo, HttpServletRequest request,
            HttpServletResponse response)
    {
        LogUtil.info("接收请求:/deleteImgAggregation，params：" + JsonTool.toJson(deleteImgAggVo));
        String userName = getUserName(request, response);
        if (userName == null)
            ResponseTemplate.outputCn("用户信息字段无效", HttpStatusCode.PARAMETER_USERINFO_INVALID, response);
        ResponseTemplate.outputCn(imgAggregationService.deleteImgAggregation(deleteImgAggVo, userName), response);
        LogUtil.info("完成请求:/deleteImgAggregation");
    }
    
    @ApiOperation(value = "精选 --> 图片专题 --> 图片专题聚合引用", notes = "图片专题聚合引用上架或设置预约下架时间", response = ResultVo.class)
    @RequestMapping(value = "/updateImgAggregationUp ", method = { RequestMethod.POST })
    public void updateImgAggregationUp(@RequestBody UpImgAggregationVo upImgAggVo, HttpServletRequest request,
            HttpServletResponse response)
    {
        LogUtil.info("接收请求:/updateImgAggregationUp，params：" + JsonTool.toJson(upImgAggVo));
        String userName = getUserName(request, response);
        if (userName == null)
            ResponseTemplate.outputCn("用户信息字段无效", HttpStatusCode.PARAMETER_USERINFO_INVALID, response);
        ResponseTemplate.outputCn(imgAggregationService.updateImgAggregationUp(upImgAggVo, userName),
                HttpStatusCode.SUCCESS, response);
        LogUtil.info("完成请求:/updateImgAggregationUp");
    }

    @ApiOperation(value = "精选 --> 图片专题 --> 图片专题聚合引用", notes = "图片专题聚合引用下架", response = com.eebbk.common.vo.ResultVo.class)
    @RequestMapping(value = "/updateImgAggregationDown", method = { RequestMethod.POST })
    public void updateImgAggregationDown(@RequestBody DownImgAggregationVo downImgAggVo,
            HttpServletRequest request, HttpServletResponse response)
    {
        LogUtil.info("接收请求:/updateImgAggregationDown，params：" + JsonTool.toJson(downImgAggVo));
        String userName = getUserName(request, response);
        if (userName == null)
            return;
        ResponseTemplate.outputCn(imgAggregationService.updateImgAggregationDown(downImgAggVo, userName),
                HttpStatusCode.SUCCESS, response);
        LogUtil.info("完成请求:/updateImgAggregationDown");
    }
    
    /**
     * 判断图片专题上架权限
     *
     * @param checkImgFeaturePublishAccessVo
     * @param response
     * @param request
     * @return "void"
     *
     * @author 孙泽宇
     * @date 2018/12/3 11:35
     **/
    @RequestMapping(value = "/checkPublishAccess", method = { RequestMethod.POST })
    public void checkPublishAccess(@RequestBody CheckImgFeaturePublishAccessVo checkImgFeaturePublishAccessVo,
            HttpServletResponse response, HttpServletRequest request)
    {
        String userName = getUserName(request, response);
        if (userName == null)
            return;
        ResponseTemplate.outputCn(imgFeatureService.checkPublishAccess(checkImgFeaturePublishAccessVo, userName),
                HttpStatusCode.SUCCESS, response);
    }

    /**
     * 获取用户名
     *
     * @param
     * @return "java.lang.String"
     *
     * @author 孙泽宇
     * @date 2018/12/3 11:27
     **/
    private String getUserName(HttpServletRequest request, HttpServletResponse response)
    {
        String userName;
        if (ToolUtil.IS_VALIDATION)
        {
            UserInfo userRole = userInfoService.getUser(request);
            if (userRole == null)
            {
                // 无权限
                ResponseTemplate.outputCn(HttpStatusCode.OP_PERMISSIONS_INVALID, response);
                return null;
            }
            userName = userRole.getRealName();
        }
        else
        {
            userName = "测试";
        }
        return userName;
    }
    
}
