import { useState } from "react";
import SignInForm from "../../features/auth/components/SignInForm";
import SignUpForm from "../../features/auth/components/SignUpForm";

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <main className="min-h-screen bg-slate-50 lg:grid lg:grid-cols-2">
      <section className="relative hidden min-h-screen overflow-hidden bg-slate-950 px-12 py-10 text-white lg:flex lg:flex-col lg:justify-center xl:px-20">
        <div className="relative z-10 flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-blue-500 shadow-lg shadow-blue-500/20">
            <svg
              viewBox="0 0 24 24"
              fill="none"
              className="h-6 w-6 text-white"
              stroke="currentColor"
              strokeWidth="2"
            >
              <path
                d="M12 21s7-5.5 7-12a7 7 0 1 0-14 0c0 6.5 7 12 7 12Z"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
              <circle cx="12" cy="9" r="2.5" />
            </svg>
          </div>

          <span className="text-xl font-semibold tracking-tight">
            SmartRoute
          </span>
        </div>

        <div className="relative z-10 max-w-xl">
          <h1 className="text-5xl font-semibold  tracking-tight xl:text-6xl">
            Your journey.
            <br />
            <span className="text-blue-400">Your route.</span>
            <br />
            Your choice.
          </h1>

          <p className="mt-7 max-w-md text-lg leading-8 text-slate-400">
            Discover routes, compare travel options and find the best way to get
            where you need to go.
          </p>

          <div className="mt-12 max-w-md rounded-2xl border border-white/10 bg-white/[0.04] p-5 backdrop-blur-sm">
            <div className="flex items-center justify-between">
              <span className="text-sm font-medium text-slate-300">
                Route comparison
              </span>
            </div>

            <div className="mt-5 space-y-4">
              <div className="flex items-center gap-3">
                <div className="h-2.5 w-2.5 rounded-full bg-blue-400" />
                <div className="h-px flex-1 bg-slate-700" />
                <span className="text-sm text-slate-300">Walking</span>
                <span className="text-sm font-medium text-white">24 min</span>
              </div>

              <div className="flex items-center gap-3">
                <div className="h-2.5 w-2.5 rounded-full bg-indigo-400" />
                <div className="h-px flex-1 bg-slate-700" />
                <span className="text-sm text-slate-300">Cycling</span>
                <span className="text-sm font-medium text-white">12 min</span>
              </div>

              <div className="flex items-center gap-3">
                <div className="h-2.5 w-2.5 rounded-full bg-emerald-400" />
                <div className="h-px flex-1 bg-slate-700" />
                <span className="text-sm text-slate-300">Driving</span>
                <span className="text-sm font-medium text-white">8 min</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="flex min-h-screen flex-col items-center justify-center px-5 py-10 sm:px-8">
        <div className="mb-10 flex items-center gap-2.5 lg:hidden">
          <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-blue-600 text-white">
            <svg
              viewBox="0 0 24 24"
              fill="none"
              className="h-5 w-5"
              stroke="currentColor"
              strokeWidth="2"
            >
              <path
                d="M12 21s7-5.5 7-12a7 7 0 1 0-14 0c0 6.5 7 12 7 12Z"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
              <circle cx="12" cy="9" r="2.5" />
            </svg>
          </div>
          <span className="text-xl font-semibold tracking-tight text-slate-900">
            SmartRoute
          </span>
        </div>

        <div className="w-full max-w-md">
          <div className="mb-8">
            <p className="text-sm font-semibold uppercase tracking-wider ">
              Welcome to SmartRoute
            </p>
            <h2 className="mt-3 text-3xl font-semibold tracking-tight text-slate-900">
              {isLogin ? "Welcome back" : "Create your account"}
            </h2>
          </div>

          <div className="rounded-2xl border border-slate-200/80 bg-white p-6 shadow-xl shadow-slate-200/50 sm:p-8">
            {isLogin ? <SignInForm /> : <SignUpForm />}

            <div className="mt-7 border-t border-slate-100 pt-6 text-center">
              <p className="text-sm text-slate-500">
                {isLogin
                  ? "Don't have an account?"
                  : "Already have an account?"}
                <button
                  type="button"
                  onClick={() => setIsLogin((previous) => !previous)}
                  className="ml-1.5 font-semibold text-blue-600 transition-colors hover:text-blue-700"
                >
                  {isLogin ? "Create account" : "Sign in"}
                </button>
              </p>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

export default AuthPage;
