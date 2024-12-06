import { useEffect, useState } from "react";

import { addItemToWatchlist, getUserWatchlist } from "@/Redux/Watchlist/Action";
import { useDispatch, useSelector } from "react-redux";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { BookmarkFilledIcon } from "@radix-ui/react-icons";
import { existInWatchlist } from "@/Util/existInWatchlist";

const Watchlist = () => {
  const dispatch = useDispatch();
  const [page, setPage] = useState(1);
  const { watchlist,coin } = useSelector((store) => store);
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(getUserWatchlist(localStorage.getItem("jwt")));
  }, [page]);

  const handleAddToWatchlist=(id)=>{
    dispatch(addItemToWatchlist(id))
  }


  

  return (
    <div className="pt-8 lg:px-10 text-black">
        <div className="flex items-center pt-5 pb-10 gap-5">
            <BookmarkFilledIcon className="h-10 w-10"/>
        <h1 className=" text-4xl font-semibold">Watchlist</h1> 
        </div>
       
      <Table className="px-5 lg:px-20  border-t relative border-x border-b p-10 ">
        <ScrollArea className={""}>
          <TableHeader>
            <TableRow className="sticky top-0 left-0 right-0 bg-background">
              <TableHead className="py-4">Coin</TableHead>
              <TableHead>SYMBOL</TableHead>
              <TableHead>VOLUME</TableHead>
              <TableHead>MARKET CAP</TableHead>
              <TableHead>24H</TableHead>
              <TableHead className="">PRICE</TableHead>
              <TableHead className="text-right text-red-700">Remove</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody className="">
            {/* {watchlist.items?.map((item) => ( */}
              <TableRow className="" key="bitcoin">
                <TableCell
                  onClick={() => navigate(`/market/bitcoin`)}
                  className="font-medium flex items-center gap-2 cursor-pointer"
                >
                  <Avatar className="-z-50">
                    <AvatarImage src={coin.coinDetails.image} alt={coin.coinDetails.id} />
                  </Avatar>
                  <span> {coin.coinDetails.id}</span>
                </TableCell>
                <TableCell>{coin.coinDetails.symbol.toUpperCase()}</TableCell>
                <TableCell>{coin.coinDetails.total_volume}</TableCell>
                <TableCell>{coin.coinDetails.market_cap}</TableCell>
                <TableCell
                  className={`${
                    coin.coinDetails.market_cap_change_percentage_24h < 0
                      ? "text-red-600"
                      : "text-green-600"
                  }`}
                >
                  {coin.coinDetails.market_cap_change_percentage_24h}%
                </TableCell>
                <TableCell>{coin.coinDetails.current_price}</TableCell>

                <TableCell className="text-right">
                   <Button
                    variant ="ghost" 
                    onClick={
                      ()=> {}}
                      size="icon"
                      className="h-10 w-10"
                      >
                        
                        <BookmarkFilledIcon className="h-6 w-6" />
                    </Button>
                </TableCell>
              </TableRow>
            
          </TableBody>
        </ScrollArea>
      </Table>
    </div>
  );
};

export default Watchlist;
