import type { ReactNode } from "react";

type AuthButtonPropsType = {
    children: ReactNode;
    disabled?: boolean;
};


const AuthButton = ({disabled, children}:AuthButtonPropsType) => {
    return ( 
              <button
        type="submit"
        disabled={disabled}
        className="
          flex w-full items-center justify-center gap-2 rounded-xl
          bg-blue-600 px-4 py-3.5 text-sm font-semibold text-white
          shadow-lg shadow-blue-600/20
          transition-all duration-200
          hover:-translate-y-0.5 hover:bg-blue-700 hover:shadow-xl
          hover:shadow-blue-600/20
          focus:outline-none focus:ring-4 focus:ring-blue-500/20
          disabled:translate-y-0 disabled:cursor-not-allowed
          disabled:opacity-60 disabled:shadow-none
        "
      >
        {children}
      </button>
     );
}
 
export default AuthButton;