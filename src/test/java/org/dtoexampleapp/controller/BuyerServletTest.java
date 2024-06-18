package org.dtoexampleapp.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dtoexampleapp.dto.BuyerDto;
import org.dtoexampleapp.service.ServiceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class BuyerServletTest extends HttpServlet {
    private HttpServletResponse resp;
    private HttpServletRequest req;
    private ServiceOperation serviceOperation;
    private BuyerServlet buyerServlet;
    private BuyerDto buyerDto;

    @BeforeEach
    public void setUp() {
        resp = mock(HttpServletResponse.class);
        req = mock(HttpServletRequest.class);
        serviceOperation = mock(ServiceOperation.class);
        buyerDto = new BuyerDto();
        buyerServlet = new BuyerServlet(serviceOperation, buyerDto);
    }

    @Test
    public void doGet() throws IOException {
        String path = "e:\\Home\\test.txt";
        PrintWriter printWriter = Mockito.spy(new PrintWriter(path));
        List<BuyerDto> buyerDtos = new ArrayList<>();
        buyerDtos.add(new BuyerDto(1, "Lex", 1200, null, null));
        buyerDtos.add(new BuyerDto(2, "Max", 1200, null, null));
        when(serviceOperation.findAll()).thenReturn(buyerDtos);
        when(resp.getWriter()).thenReturn(printWriter);
        buyerServlet.doGet(req, resp);
        verify(serviceOperation).findAll();
    }

    @Test
    public void doPost() {
        when(req.getParameter("buyer_id")).thenReturn("1");
        when(req.getParameter("buyer_name")).thenReturn("Max");
        when(req.getParameter("buyer_balance")).thenReturn("1000");
        Mockito.doNothing().when(serviceOperation).add(buyerDto);
        buyerServlet.doPost(req, resp);
        verify(serviceOperation).add(buyerDto);
    }

    @Test
    public void doPut() {
        when(req.getParameter("buyer_id")).thenReturn("1");
        when(req.getParameter("buyer_name")).thenReturn("Max");
        when(req.getParameter("buyer_balance")).thenReturn("1000");
        Mockito.doNothing().when(serviceOperation).update(buyerDto);
        buyerServlet.doPut(req, resp);
        verify(serviceOperation).update(buyerDto);
    }

    @Test
    public void doDelete() {
        when(req.getParameter("buyer_id")).thenReturn("1");
        Mockito.doNothing().when(serviceOperation).deleteById(1);
        buyerServlet.doDelete(req, resp);
        verify(serviceOperation).deleteById(1);
    }
}