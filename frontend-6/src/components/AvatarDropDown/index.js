/* eslint-disable no-console */
/* --------------------------------------------------------
* Author Trần Đức Tiến
* Email tientran0019@gmail.com
* Phone 0972970075
*
* Created: 2020-03-01 17:15:11
*------------------------------------------------------- */

import React from 'react';
import PropTypes from 'prop-types';

import { Menu, Dropdown, Modal } from 'antd';
import { useSelector, useDispatch } from 'react-redux';

import Router from 'next/router';
import Link from 'next/link';

import { BiKey } from 'react-icons/bi';
import { AiOutlineLogout } from 'react-icons/ai';

import {
	ExclamationCircleOutlined,
} from '@ant-design/icons';

import Avatar from 'src/components/Avatar';

import classes from './style.module.less';
import { LoginService } from 'src/service/LoginService';

const propTypes = {
	style: PropTypes.object,
};

const defaultProps = {
	style: {},
};

const AvatarDropDown = (props) => {

	const loginService = new LoginService();
	
	const { style } = props;

	const auth = {}

	const paid = {}

	//const auth = useSelector(state => state.auth);

	//const { paymentDetail: { paid } = {} } = auth || {};

	//const dispatch = useDispatch();

	const handleLogout = React.useCallback(async () => {
		Modal.confirm({
			title: 'Are you sure?',
			icon: <ExclamationCircleOutlined />,
			// content: 'Are you sure?',
			onOk: async () => {loginService.doLogout()},
			onCancel() {
				console.log('Cancel');
			},
		});
	}, []);
	//}, [dispatch]);

	const menu = (
		<Menu className={classes.menuDropdown}>
			<div className={classes.name}>
				<Avatar
					size={50}
					src={auth.avatar}
					fullName={auth.fullName}
					vip={paid}
				/>
				<div className={classes.fullName}>
					<strong>{auth.fullName}</strong>
					<div className="text-small">{auth.email}</div>
				</div>
			</div>
			<Menu.Divider />
			{/* <Menu.Item>
				<Link href="/profile" className={classes.item}>
						<FiUser />
						<span>Profile</span>
				</Link>
			</Menu.Item> */}
			<Menu.Item key="change">
				<Link href="/change-password" className={classes.item}>
					<BiKey />
					<span>Change password</span>
				</Link>
			</Menu.Item>
			<Menu.Item key="logout">
				<a className={classes.item} onClick={handleLogout}>
					<AiOutlineLogout />
					<span>Logout</span>
				</a>
			</Menu.Item>
		</Menu>
	);

	return (
		<Dropdown style={style} overlay={menu} trigger={['click']}>
			<div style={{ lineHeight: '50px' }}>
				<Avatar
					size={30}
					src={auth.avatar}
					fullName={auth.fullName}
					style={{
						cursor: 'pointer',
					}}
					vip={paid}
				/>
			</div>
		</Dropdown>
	);
};

AvatarDropDown.propTypes = propTypes;
AvatarDropDown.defaultProps = defaultProps;

export default AvatarDropDown;
