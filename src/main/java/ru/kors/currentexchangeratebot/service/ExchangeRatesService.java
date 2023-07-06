package ru.kors.currentexchangeratebot.service;

import ru.kors.currentexchangeratebot.exception.ServiceException;

public interface ExchangeRatesService {
    String getUSDExchangeRate() throws ServiceException;
    String getEURExchangeRate() throws ServiceException;
    String getAMDExchangeRate() throws ServiceException;
    String getCNYExchangeRate() throws ServiceException;
    String getKZTExchangeRate() throws ServiceException;
    String getTRYExchangeRate() throws ServiceException;
}
