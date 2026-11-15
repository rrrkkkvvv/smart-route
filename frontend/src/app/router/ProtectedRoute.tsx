import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../features/hooks/useAuth";

const PrivateRoute = () => {
  const { status, refreshSession } = useAuth();

  if (status === "checking") {
    return <div>Checking session...</div>;
  }

  if (status === "error") {
    return (
      <div>
        <p>Unable to verify your session.</p>

        <button onClick={() => void refreshSession()}>Retry</button>
      </div>
    );
  }

  if (status === "unauthenticated") {
    return <Navigate to="/auth" replace />;
  }

  return <Outlet />;
};

export default PrivateRoute;
