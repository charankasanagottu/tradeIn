/* eslint-disable no-unused-vars */
import "./Auth.css";
import { Button } from "@/components/ui/button";

import SignupForm from "./signup/SignupForm";
import LoginForm from "./login/login";
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import { Card } from "@/components/ui/card";
import ForgotPassword from "./ForgotPassword";
import ForgotPasswordForm from "./ForgotPassword";
import { Skeleton } from "@/components/ui/skeleton";
import { useSelector } from "react-redux";
import SpinnerBackdrop from "@/components/custome/SpinnerBackdrop";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { AvatarFallback } from "@radix-ui/react-avatar";
import { ToastAction } from "@/components/ui/toast";
import { useToast } from "@/components/ui/use-toast";
import CustomeToast from "@/components/custome/CustomeToast";

const Auth = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { auth } = useSelector((store) => store);
  const { toast } = useToast();

  const [animate, setAnimate] = useState(false);

  const handleNavigation = (path) => {
    // setAnimate(true);
    // setTimeout(() => {
    navigate(path);
    //   setAnimate(false);
    // }, 500);
    // Adjust the delay as needed to match your animation duration
    // setAnimate(false)
  };

  const [showToast, setShowToast] = useState(false);

  const handleShowToast = () => {
    setShowToast(true);
  };

console.log("---------- ",auth.error)


  return (
    <div className={`authContainer h-screen relative`}>
      <div className="absolute top-0 right-0 left-0 bottom-0  bg-[#addfff] bg-opacity-50 rounded-2xl"></div>

      <div
        className={`bgBlure absolute top-1/2 left-1/2 transform  bg-[#ffffff] -translate-x-1/2 -translate-y-1/2 box flex flex-col justify-center items-center  h-[35rem] w-[30rem]  rounded-md z-50  bg-opacity-90 shadow-2xl shadow-white text-black`}
      >
         <CustomeToast show={auth.error} message={auth.error?.error}/>
     
         <Avatar  style={{ width: '125px', height: '125px' }}>
          <AvatarImage src="../../../public/TradeIN.png"/>
          <AvatarFallback>BTC</AvatarFallback>
          
        </Avatar>
        <h1 className="text-3xl font-bold pb-5"></h1>
        

        {location.pathname == "/signup" ? (
          <section
            className={`w-full login  ${animate ? "slide-down" : "slide-up"}`}
          >
            <div className={`  loginBox  w-full px-10 space-y-5 `}>
              <SignupForm />

              {location.pathname == "/signup" ? (
                <div className="flex items-center justify-center">
                  <span> {"don't have account ?"} </span>
                  <Button
                    onClick={() => handleNavigation("/signin")}
                    className="bg-[#ffffff] hover:bg-[#38b5ff]"
                  >
                    Login 
                  </Button>
                </div>
              ) : (
                <div className="flex items-center justify-center">
                  <span >Already have account ? </span>
                  <Button
                    onClick={() => handleNavigation("/signup")}
                    variant="ghost"
                    className="bg-[#38b6ff] hover:bg-[#5271ff]"
                  >
                    signup
                  </Button>
                </div>
              )}
            </div>
          </section>
        ) : location.pathname == "/forgot-password" ? (
          <section className="p-5 w-full">
            <ForgotPasswordForm />
            {/* <Button variant="outline" className="w-full py-5 mt-5">
              Try Using Mobile Number
            </Button> */}
            <div className="flex items-center justify-center mt-5">
              <span>Back To Login ? </span>
              <Button onClick={() => navigate("/signin")} variant="ghost" className="bg-[#38b6ff] hover:bg-[#5271ff]">
                Login
              </Button>
            </div>
          </section>
        ) : (
          <>
            {
              <section className={`w-full login`}>
                <div className={`  loginBox  w-full px-10 space-y-5 `}>
                  <LoginForm />

                  <div className="flex items-center justify-center">
                    <span>already have account ? </span>
                    <Button
                      onClick={() => handleNavigation("/signup")}
                      className="bg-[#ffffff] hover:bg-[#38b5ff] "
                    >
                      Signup
                    </Button>
                  </div>
                  <div className="">
                    <Button
                      onClick={() => navigate("/forgot-password")}
                      
                      className="w-full py-5 bg-[#38b6ff] hover:bg-[#5271ff]"
                    >
                      Forgot Password ?
                    </Button>
                  </div>
                </div>
              </section>
            }
          </>
        )}


      </div>
      
    

    </div>
  );
};

export default Auth;
