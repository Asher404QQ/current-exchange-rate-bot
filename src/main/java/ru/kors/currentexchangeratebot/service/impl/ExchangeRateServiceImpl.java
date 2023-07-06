package ru.kors.currentexchangeratebot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import ru.kors.currentexchangeratebot.client.CbrClient;
import ru.kors.currentexchangeratebot.exception.ServiceException;
import ru.kors.currentexchangeratebot.service.ExchangeRatesService;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
public class ExchangeRateServiceImpl implements ExchangeRatesService {
    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";
    private static final String AMD_XPATH = "/ValCurs//Valute[@ID='R01060']/Value";
    private static final String CNY_XPATH = "/ValCurs//Valute[@ID='R01375']/Value";
    private static final String KZT_XPATH = "/ValCurs//Valute[@ID='R01335']/Value";
    private static final String TRY_XPATH = "/ValCurs//Valute[@ID='R01700J']/Value";
    @Autowired
    private CbrClient cbrClient;

    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = cbrClient.getCurrentRate();
        return extractCurrentValueFromXML(xml, USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = cbrClient.getCurrentRate();
        return extractCurrentValueFromXML(xml, EUR_XPATH);
    }

    @Override
    public String getAMDExchangeRate() throws ServiceException {
        var xml = cbrClient.getCurrentRate();
        return extractCurrentValueFromXML(xml, AMD_XPATH);
    }

    @Override
    public String getCNYExchangeRate() throws ServiceException {
        var xml = cbrClient.getCurrentRate();
        return extractCurrentValueFromXML(xml, CNY_XPATH);
    }

    @Override
    public String getKZTExchangeRate() throws ServiceException {
        var xml = cbrClient.getCurrentRate();
        return extractCurrentValueFromXML(xml, KZT_XPATH);
    }

    @Override
    public String getTRYExchangeRate() throws ServiceException {
        var xml = cbrClient.getCurrentRate();
        return extractCurrentValueFromXML(xml, TRY_XPATH);
    }

    private static String extractCurrentValueFromXML(String xml, String xpathExpression) throws ServiceException{
        var source = new InputSource(new StringReader(xml));
        try {
            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
            return xpath.evaluate(xpathExpression, document);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Unable to parse XML", e);
        }
    }
}
