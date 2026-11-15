import { useState, type FormEvent } from "react";
import { useAuth } from "../../hooks/useAuth";
import { authApi } from "../services/authApi";
import AuthInput from "./AuthInput";
import AuthButton from "./AuthButton";

const inputClassName = `
  w-full rounded-xl border border-slate-200 bg-white px-4 py-3
  text-sm text-slate-900 placeholder:text-slate-400
  outline-none transition-all duration-200
  hover:border-slate-300
  focus:border-blue-500 focus:ring-4 focus:ring-blue-500/10
  disabled:cursor-not-allowed disabled:bg-slate-50
`;

const labelClassName = "mb-2 block text-sm font-medium text-slate-700";

const SignInForm = () => {
  const { signIn } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    setError(null);
    setIsLoading(true);

    try {
      const authResponse = await authApi.signIn({
        email,
        password,
      });

      signIn(authResponse);
    } catch {
      setError("Invalid email or password. Please try again.");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <label htmlFor="email" className={labelClassName}>
          Email address
        </label>
        <AuthInput
          id="email"
          type="email"
          placeholder="you@example.com"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          className={inputClassName}
          required
          disabled={isLoading}
        />
      </div>

      <div>
        <div className="mb-2 flex items-center justify-between">
          <label
            htmlFor="password"
            className="text-sm font-medium text-slate-700"
          >
            Password
          </label>
        </div>
        <AuthInput
          id="password"
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          className={inputClassName}
          required
          disabled={isLoading}
        />
      </div>

      {error && (
        <div
          role="alert"
          className="flex items-start gap-3 rounded-xl border border-red-100 bg-red-50 px-4 py-3 text-sm text-red-700"
        >
          <span className="mt-0.5 font-semibold">!</span>
          <p>{error}</p>
        </div>
      )}

      <AuthButton disabled={isLoading}>
        {isLoading ? (
          <>
            <span className="h-4 w-4 animate-spin rounded-full border-2 border-white/30 border-t-white" />
            Signing in...
          </>
        ) : (
          "Sign in"
        )}
      </AuthButton>
    </form>
  );
};

export default SignInForm;
