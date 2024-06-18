package org.dtoexampleapp.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dtoexampleapp.dto.ShopDto;
import org.dtoexampleapp.service.ServiceOperation;
import java.io.IOException;
import java.util.List;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {
    private final ServiceOperation serviceOperation;
    private final ShopDto shopDto;

    public ShopServlet(ServiceOperation serviceOperation, ShopDto shopDto) {
        this.serviceOperation = serviceOperation;
        this.shopDto = shopDto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ShopDto> shopDtos = serviceOperation.findAll();
        String json = new Gson().toJson(shopDtos);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("shop_id"));
        String name = req.getParameter("shop_name");
        int contactNumber = Integer.parseInt(req.getParameter("shop_contact"));
        shopDto.setId(id);
        shopDto.setName(name);
        shopDto.setContactNumber(contactNumber);
        serviceOperation.add(shopDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("shop_id"));
        String name = req.getParameter("shop_name");
        int contactNumber = Integer.parseInt(req.getParameter("shop_contact"));
        shopDto.setId(id);
        shopDto.setName(name);
        shopDto.setContactNumber(contactNumber);
        serviceOperation.update(shopDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("shop_id"));
        serviceOperation.deleteById(id);
    }
}
