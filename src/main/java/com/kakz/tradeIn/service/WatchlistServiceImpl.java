package com.kakz.tradeIn.service;

import com.kakz.tradeIn.model.Coin;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.model.Watchlist;
import com.kakz.tradeIn.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistRepository  watchlistRepository;
    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if(watchlist==null){
            throw new Exception("watchlist is not found");
//            return createWatchList(user);
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchList(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);

        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {

        return watchlistRepository
                .findById(id)
                .orElseThrow(
                        ()-> new Exception("Watchlist not found"));
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());
        if(watchlist.getCoinList().contains(coin)){
            watchlist.getCoinList().remove(coin);
        }
        else{
            watchlist.getCoinList().add(coin);
        }
        watchlistRepository.save(watchlist);
        return coin;
    }
}
