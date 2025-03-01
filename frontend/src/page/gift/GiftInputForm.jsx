import React, {useState} from 'react';
import {Button, TextField} from "@mui/material";

const initGift = {
  name: '',
  price: '',
  memo: ''
};

const GiftInputForm = props => {
  const [newGift, setNewGift] = useState(initGift);

  const setProperties = (e) => {
    setNewGift({...newGift, [e.target.name]: e.target.value});
  }

  const submitHandler = e => {
    e.preventDefault();
    props.onGiftSave(newGift);
    setNewGift(initGift)
  }

  return (
    <div style={{display: 'flex', justifyContent: 'flex-start'}}>
      <TextField style={{margin: '0 5px'}} name="name" label="이름" variant="outlined" size="small" color="success"
                 value={newGift.name} onChange={setProperties}/>
      <TextField style={{margin: '0 5px'}} name="price" label="금액" variant="outlined" size="small" color="success"
                 value={newGift.price} onChange={setProperties} type="number"/>
      <TextField style={{margin: '0 5px'}} name="memo" label="비고" variant="outlined" size="small" color="success"
                 value={newGift.memo} onChange={setProperties}/>
      <Button type="submit" variant="outlined" style={{margin: '0 5px'}} color="success"
              onClick={submitHandler}>저장</Button>
    </div>
  );
};

export default GiftInputForm;