package org.dtoexampleapp.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dtoexampleapp.dto.BuyerDto;
import org.dtoexampleapp.service.ServiceOperation;
import java.io.IOException;
import java.util.List;

@WebServlet("/buyer")
public class BuyerServlet extends HttpServlet {
    private final ServiceOperation serviceOperation;
    private final BuyerDto buyerDto;

    public BuyerServlet(ServiceOperation serviceOperation, BuyerDto buyerDto) {
        this.serviceOperation = serviceOperation;
        this.buyerDto = buyerDto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<BuyerDto> buyerDtos = serviceOperation.findAll();
        String json = new Gson().toJson(buyerDtos);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("buyer_id"));
        String name = req.getParameter("buyer_name");
        int balance = Integer.parseInt(req.getParameter("buyer_balance"));
        buyerDto.setBalance(balance);
        buyerDto.setName(name);
        buyerDto.setId(id);
        serviceOperation.add(buyerDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("buyer_id"));
        String name = req.getParameter("buyer_name");
        int balance = Integer.parseInt(req.getParameter("buyer_balance"));
        buyerDto.setBalance(balance);
        buyerDto.setName(name);
        buyerDto.setId(id);
        serviceOperation.update(buyerDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("buyer_id"));
        serviceOperation.deleteById(id);
    }
}
