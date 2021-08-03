package com.rookied.controller;

import com.rookied.bean.Emp;
import com.rookied.dao.EmpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhangqiang
 * @date 2021/8/3
 */
@Controller
public class EmpController {
    @Autowired
    EmpDao empDao;

    @RequestMapping("/emp")
    public ModelAndView query(Integer empno) {
        Emp emp = empDao.findEmpByEmpno(empno);
        ModelAndView mv = new ModelAndView();
        mv.addObject("ename", emp.getEname());
        mv.setViewName("success");
        return mv;
    }
}
