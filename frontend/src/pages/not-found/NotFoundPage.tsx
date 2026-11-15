import { Link } from "react-router";

const NotFoundPage = () => {
  return (
    <div>
      <h1>404 - Page not found</h1>
      <Link to="/planner">Go to planner</Link>
    </div>
  );
};

export default NotFoundPage;
