package com.gupaoedu.controller;

import com.gupaoedu.entity.Merchant;
import com.gupaoedu.util.Constant;
import com.gupaoedu.util.LayuiData;
import com.gupaoedu.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    /**
     * 查询商户列表
     * @return
     */
    @RequestMapping("/getMerchantList")
    @ResponseBody
    public LayuiData getMerchantList (HttpServletRequest request){
        String name = request.getParameter("name");
        if(name==null){
            name="";
        }
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>=1){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        List<Merchant> merchantList = merchantService.getMerchantList(name,page,limit);
        int count = merchantService.getMerchantCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(merchantList);
       return layuiData;
    }

    /**
     * 去新增商户界面
     * @return
     */
    @RequestMapping("/toMerchant")
    public String toMerchant (){

        return "merchantAdd";
    }

    /**
     * 新增商户
     * @param name
     * @param
     * @return
     */
    @RequestMapping("/merchantAdd")
    @Transactional
    @ResponseBody
    public Integer merchantAdd (String name,String address,String accountNo, String accountName){
        Merchant merchant = new Merchant();
        merchant.setAccountNo(accountNo);
        merchant.setName(name);
        merchant.setAddress(address);
        merchant.setAccountName(accountName);
        merchant.setState(Constant.MERCHANT_STATE.ACITVE);
        int num = merchantService.add(merchant);
        return num;
    }

    @RequestMapping("/merchantList")
    public String merchantList1() {
        return "merchantListPage";
    }

    /**
     * 根据id删除商户
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer id) {

       int num = merchantService.delete(id);

        return num;
    }

    /**
     * 去查看界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail(Integer id, Model model) {

        Merchant merchant = merchantService.getMerchantById(id);
        model.addAttribute("merchant",merchant);
        return "merchantDetail";
    }
    /**
     * 去修改界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {

        Merchant merchant = merchantService.getMerchantById(id);
        model.addAttribute("merchant",merchant);
        return "merchantUpdate";
    }
    /**
     * 根据id修改商户信息
     * @return
     */
    @RequestMapping("/merchantUpdate")
    @Transactional
    @ResponseBody
    public Integer merchantUpdate (Integer id, String name,String address,String accountNo, String accountName){
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setName(name);
        merchant.setAddress(address);
        merchant.setAccountNo(accountNo);
        merchant.setAccountName(accountName);
        int num = merchantService.update(merchant);
        return num;
    }

    /**
     * 变更商户状态
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/changeState")
    @ResponseBody
    public String changeState(@RequestParam(value = "id") String idStr)throws Exception{
        String errmsg="";
        if( null == idStr || "".equals(idStr))
        return "商户号不能为空";
        int id = Integer.parseInt(idStr);

        // 校验
        Merchant result = merchantService.getMerchantById(id);
        if (null == result) {
            return "编号为" + id + "的商户不存在！";
        }

        Merchant updateBean = new Merchant();
        updateBean.setId(id);
        //如果是现在是启用，则停用
        if(Constant.MERCHANT_STATE.ACITVE.equals(result.getState())){
            updateBean.setState("0");
        }else {
            updateBean.setState("1");
        }

        int num = merchantService.updateState(updateBean);
        // 1表示成功
        if( num == 1){
            return "1";
        }else{
            return "更新商户状态失败";
        }

    }
}
