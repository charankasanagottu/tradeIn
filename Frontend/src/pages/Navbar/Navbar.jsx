import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  AvatarIcon,
  DragHandleHorizontalIcon,
  MagnifyingGlassIcon,
} from "@radix-ui/react-icons";
import SideBar from "../SideBar/SideBar";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { useNavigate } from "react-router-dom";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { useState } from "react";
import { useSelector } from "react-redux";

const Navbar = () => {
  const navigate = useNavigate();
  const { auth } = useSelector((store) => store);

  const handleNavigate=()=>{
    if(auth.user){
      auth.user.role==="ROLE_ADMIN"?navigate("/admin/withdrawal"):navigate("/profile")
    }
  }
  return (
    
      <div className="px-2 py-3 z-50 bg-[#5c61e6] bg-opacity-75 sticky top-0 left-0 right-0 flex justify-between items-center text-black">
        <div className="flex items-center gap-3">
          <Sheet className="text-black">
            <SheetTrigger>
              <Button
                className="rounded-full h-11 w-11 hover:bg-[#5c61e6] hover:bg-opacity-20"
                
                size="icon"
              >
                <DragHandleHorizontalIcon className=" h-7 w-7" />
              </Button>
            </SheetTrigger>
            <SheetContent
              className="w-72  border-r-0 flexs flex-col  justify-center  bg-[#ffffff] bg-opacity-20"
              side="left" 
            >
              <SheetHeader>
                <SheetTitle>
                  <div className="text-3xl flex justify-center  items-center gap-1">
                    <Avatar className="h-20 w-20">
                      <AvatarImage src="../../../public/TradeIN.png" />
                    </Avatar>
                    <div>
                      <span className="font-bold">Trade</span>
                      <span className=" text-[#38b6ff] bg-opacity-75">In</span>
                    </div>
                  </div>
                </SheetTitle>
              </SheetHeader>
              <SideBar  />
            </SheetContent>
          </Sheet>

          <p
            onClick={() => navigate("/")}
            className="text-3xl cursor-pointer"
          >
            <span className="text-white">Trade</span>In 
          </p>
          <div className="p-0 ml-9 text-black">
            <Button
              variant="outline"
              onClick={() => navigate("/search")}
              className="flex bg-[#ffffff] hover:bg-[#708ae7] hover:bg-opacity-5 rounded-full"
            >
              {" "}
              <MagnifyingGlassIcon className="left-2 top-3 text-black " />
              <span>Search</span>
            </Button>
          </div>
        </div>
        <div>
          <Avatar className="cursor-pointer" onClick={handleNavigate}>
            {!auth.user ? (
              <AvatarIcon className=" h-8 w-8" />
            ) : (
              <AvatarFallback className="bg-[#ffffff]">{auth.user?.fullName[0].toUpperCase()}</AvatarFallback>
            )}
          </Avatar>
        </div>
      </div>
    
  );
};

export default Navbar;
