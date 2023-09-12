import React from 'react';

const GiftHeader = props => {

  const {total, totalPrice} = props;
  return (
    <div style={{display: "flex", justifyContent: 'space-between'}}>
      <div style={{display: "flex", justifyContent: 'flex-start'}}>
        <p>총: {total}명 /</p>
        <p style={{marginLeft: 5}}>{totalPrice.toLocaleString()}원</p>
      </div>
    </div>
  );
};

export default GiftHeader;