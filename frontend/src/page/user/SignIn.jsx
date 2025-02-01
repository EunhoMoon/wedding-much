import React, {useState} from 'react';
import classes from "./css/SignIn.module.css";
import {Button, Paper, TextField} from "@mui/material";
import axios from "axios";
import {useDispatch} from "react-redux";
import {signin} from "../../store/authSlice";
import {useNavigate} from "react-router-dom";

export const SignIn = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [emailIsValid, setEmailIsValid] = useState(true);
  const [passwordIsValid, setPasswordIsValid] = useState(true);

  const inputChangeHandler = (e) => {
    const {id, value} = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  const signInHandler = async (e) => {
    e.preventDefault();

    const isEmailValid = validateEmail(formData.email);
    const isPasswordValid = validatePassword(formData.password);

    setEmailIsValid(isEmailValid);
    setPasswordIsValid(isPasswordValid);

    if (!isEmailValid || !isPasswordValid) {
      return;
    }

    const jwt = await signIn();
    dispatch(signin(jwt));
    navigate('/');
  };

  const signIn = async () => {
    try {
      const response = await axios.post('/api/users/login', {
        email: formData.email,
        password: formData.password
      });
      if (response.status === 200) {
        return response.data;
      }
    } catch (e) {
      console.log(e);
      if (e.status === 401) {
        alert('잘못된 Email 주소 또는 비밀번호입니다. 다시 확인하여 주세요.');
      }
      if (e.status === 500) {
        alert('서버에 문제가 발생하였습니다. 잠시 후 다시 시도하여 주세요.');
      }
    }
  }

  const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return email.length > 0 && emailRegex.test(email);
  }

  const validatePassword = (password) => {
    return password.length > 0;
  }

  return (
    <div className={classes.wrapper}>
      <Paper className={classes.loginBox}>
        <div className={classes.userForm}>
          <div className={classes.titleWrapper}>
            <h2>Sign In</h2>
          </div>
          <form>
            <div className={classes.inputWrapper}>
              <TextField
                id="email"
                label="Email Address"
                variant="outlined"
                className={classes.userInput}
                onChange={inputChangeHandler}
                {...(emailIsValid ? {} : {error: true})}
              />
              {!emailIsValid && <p className={classes.userInputError}>잘못된 Email 주소입니다. 다시 확인하여 주세요.</p>}
            </div>
            <div className={classes.inputWrapper}>
              <TextField
                id="password"
                label="Password"
                variant="outlined"
                type="password"
                className={classes.userInput}
                onChange={inputChangeHandler}
                {...(passwordIsValid ? {} : {error: true})}
              />
              {!passwordIsValid && <p className={classes.userInputError}>비밀번호는 8자리 이상입니다. 다시 확인하여 주세요.</p>}
            </div>
            <div className={classes.buttonWrapper}>
              <Button type="submit" variant="contained" color="success" className={classes.button}
                      onClick={signInHandler}>Sign-in</Button>
              <Button variant="outlined" color="success" className={classes.button}>Sign-Up</Button>
              <Button variant="outlined" color="warning" className={classes.button}>KaKao</Button>
              <Button variant="outlined" color="info" className={classes.button}>Google</Button>
            </div>
          </form>
        </div>
      </Paper>
    </div>
  );
};