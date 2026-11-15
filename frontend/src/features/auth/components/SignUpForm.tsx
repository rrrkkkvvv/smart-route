import { useState, type FormEvent } from "react";
import { authApi } from "../services/authApi";
import { useAuth } from "../../hooks/useAuth";
import AuthInput from "./AuthInput";
import AuthButton from "./AuthButton";

const inputClassName = `

`;

const labelClassName = "mb-2 block text-sm font-medium text-slate-700";

const SignUpForm = () => {
  const { signIn } = useAuth();

  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    setError(null);
    setIsLoading(true);

    try {
      const authResponse = await authApi.signUp({
        email,
        name: username,
        password,
      });

      signIn(authResponse);
    } catch {
      setError("Unable to create your account. Please try again.");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <label htmlFor="username" className={labelClassName}>
          Username
        </label>
        <AuthInput
          id="username"
          type="text"
          placeholder="Your username"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
          className={inputClassName}
          required
          disabled={isLoading}
        />
      </div>

      <div>
        <label htmlFor="sign-up-email" className={labelClassName}>
          Email address
        </label>
        <AuthInput
          id="sign-up-email"
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
        <label htmlFor="sign-up-password" className={labelClassName}>
          Password
        </label>
        <AuthInput
          id="sign-up-password"
          type="password"
          placeholder="Create a password"
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
            Creating account...
          </>
        ) : (
          "Create account"
        )}
      </AuthButton>
    </form>
  );
};

export default SignUpForm;
