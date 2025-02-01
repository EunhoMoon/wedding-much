import Gift from "./page/gift/Gift";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import RootLayout from "./component/RootLayout";
import {SignIn} from "./page/user/SignIn";
import {signin} from "./store/authSlice";
import {useSelector} from "react-redux";

function App() {
  const token = useSelector(state => state.auth.token);
  console.log(token);

  const router = createBrowserRouter([
    {
      path: '/',
      element: <RootLayout/>,
      children: [
        {index: true, element: <Navigate to={token != null ? "gifts" : "users/signin"} replace/>},
        {path: 'gifts', element: <Gift/>},
        {
          path: 'users', children: [
            {index: true, element: <Navigate to='signin' replace/>},
            {path: 'signin', element: <SignIn/>}
          ]
        }
      ]
    }
  ]);

  return <RouterProvider router={router}/>;
}

export default App;
