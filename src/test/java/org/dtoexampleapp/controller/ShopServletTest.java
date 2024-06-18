package org.dtoexampleapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dtoexampleapp.dto.ShopDto;
import org.dtoexampleapp.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

class ShopServletTest {
    private HttpServletResponse resp;
    private HttpServletRequest req;
    private ShopServiceImpl shopService;
    private ShopServlet shopServlet;
    private ShopDto shopDto;

    @BeforeEach
    public void setUp() {
        resp = mock(HttpServletResponse.class);
        req = mock(HttpServletRequest.class);
        shopService = mock(ShopServiceImpl.class);
        shopDto = new ShopDto();
        shopServlet = new ShopServlet(shopService, shopDto);
    }

    @Test
    void doGet() throws IOException {
        String path = "e:\\Home\\test.txt";
        PrintWriter printWriter = Mockito.spy(new PrintWriter(path));
        List<ShopDto> shopDtos = new ArrayList<>();
        shopDtos.add(new ShopDto(1, "Santa", 222324, null));
        shopDtos.add(new ShopDto(2, "Veras", 243444, null));
        when(shopService.findAll()).thenReturn(shopDtos);
        when(resp.getWriter()).thenReturn(printWriter);
        shopServlet.doGet(req, resp);
        verify(shopService).findAll();
    }

    @Test
    void doPost() {
        when(req.getParameter("shop_id")).thenReturn("1");
        when(req.getParameter("shop_name")).thenReturn("Santa");
        when(req.getParameter("shop_contact")).thenReturn("2332323");
        Mockito.doNothing().when(shopService).add(shopDto);
        shopServlet.doPost(req, resp);
        verify(shopService).add(shopDto);
    }

    @Test
    void doPut(){
        when(req.getParameter("shop_id")).thenReturn("1");
        when(req.getParameter("shop_name")).thenReturn("Santa");
        when(req.getParameter("shop_contact")).thenReturn("22344456");
        Mockito.doNothing().when(shopService).update(shopDto);
        shopServlet.doPut(req, resp);
        verify(shopService).update(shopDto);
    }

    @Test
    void doDelete() {
        when(req.getParameter("shop_id")).thenReturn("1");
        Mockito.doNothing().when(shopService).deleteById(1);
        shopServlet.doDelete(req, resp);
        verify(shopService).deleteById(1);
    }
}