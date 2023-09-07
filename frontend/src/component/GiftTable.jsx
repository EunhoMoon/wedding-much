import React from 'react';
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import GiftTableRow from "./GiftTableRow";

const GiftTable = props => {
  const {gifts, total} = props;

  return (
    <Paper sx={{width: '100%', overflow: 'hidden'}}>
      <TableContainer sx={{maxHeight: 700}}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              <TableCell style={{width: '6%'}}>#</TableCell>
              <TableCell align='center' style={{width: '30%'}}>
                이름
              </TableCell>
              <TableCell align='center' style={{width: '33%'}}>
                비고
              </TableCell>
              <TableCell align='center' style={{width: '30%'}}>
                금액
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {gifts && gifts.length > 0 && gifts.map((gift, _idx) => {
              return <GiftTableRow key={_idx} num={total - _idx} gift={gift}/>
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};

export default GiftTable;